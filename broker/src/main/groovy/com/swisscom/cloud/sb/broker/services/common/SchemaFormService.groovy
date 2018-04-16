package com.swisscom.cloud.sb.broker.services.common

import com.swisscom.cloud.sb.broker.error.ErrorCode
import com.swisscom.cloud.sb.broker.error.ServiceBrokerException
import com.swisscom.cloud.sb.broker.model.repository.CFServiceRepository
import com.swisscom.cloud.sb.broker.util.JsonHelper
import com.swisscom.cloud.sb.broker.util.JsonSchemaHelper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class SchemaFormService {
    final String SCHEMA_URI = '%1$s://%2$s:%3$s/v2/catalog/services/%4$s/plans/%5$s/schemas/%6$s/%7$s'

    private CFServiceRepository serviceRepository
    private SchemaFormServiceConfiguration schemaFormServiceConfiguration

    @Autowired
    SchemaFormService(CFServiceRepository serviceRepository, SchemaFormServiceConfiguration schemaFormServiceConfiguration) {

        this.schemaFormServiceConfiguration = schemaFormServiceConfiguration
        this.serviceRepository = serviceRepository
    }

    Map getCreateServiceInstanceSchema(String serviceGuid, String planGuid) {
        getSchema(serviceGuid, planGuid, "serviceInstanceCreateSchema")
    }

    private Map getSchema(String serviceGuid, String planGuid, String schemaPropertyName) {
        def service = serviceRepository.findByGuid(serviceGuid)
        if (service == null)
            ErrorCode.SERVICE_NOT_FOUND.throwNew()

        def plan = service.plans.find { p -> p.guid == planGuid }
        if (plan == null)
            ErrorCode.PLAN_NOT_FOUND.throwNew()

        if (!plan.hasProperty(schemaPropertyName))
            throw new ServiceBrokerException("SchemaPropertyName is incorrect")

        JsonHelper.parse(plan.getProperty(schemaPropertyName) as String, Map) as Map
    }

    String serializeAndPrepareSchema(Object schema, String serviceGuid, String planGuid, String serviceReference, String method) {
        schema = addOrUpdateId(schema, serviceGuid, planGuid, serviceReference, method)

        def serializedSchema = JsonHelper.toJsonString(schema)
        def validationMessages = JsonSchemaHelper.validateJson(serializedSchema)
        if (validationMessages.any()) {
            log.error("Invalid schema for ${serviceReference} ${method}: " + JsonHelper.toJsonString(validationMessages))
            ErrorCode.INVALID_PLAN_SCHEMAS.throwNew()
        }

        serializedSchema
    }

    private Object addOrUpdateId(Map schema, String serviceGuid, String planGuid, String serviceReference, String method) {
        schema.put("id", getSchemaUri(serviceGuid, planGuid, serviceReference, method))

        schema
    }

    private String getSchemaUri(String serviceGuid, String planGuid, String serviceReference, String method) {
        return sprintf(
                SCHEMA_URI,
                [
                        schemaFormServiceConfiguration.protocol,
                        schemaFormServiceConfiguration.hostname,
                        schemaFormServiceConfiguration.port,
                        serviceGuid,
                        planGuid,
                        serviceReference,
                        method
                ])
    }


}
