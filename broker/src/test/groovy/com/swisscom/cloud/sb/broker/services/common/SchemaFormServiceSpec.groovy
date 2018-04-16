package com.swisscom.cloud.sb.broker.services.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.swisscom.cloud.sb.broker.model.repository.CFServiceRepository
import com.swisscom.cloud.sb.broker.util.JsonHelper
import spock.lang.Specification

class SchemaFormServiceSpec extends Specification {
    private CFServiceRepository serviceRepository

    String serviceGuid = "service3-f4e9-4123-9cf2-53b252ffe2e6"
    String planGuid    = "planfc3c-263b-433b-8b12-65bbe45d9c8b"
    String defaultServiceReference = "service_instance"
    String defaultMethod = "create"

    String expectedId = "http://localhost:80/v2/catalog/services/$serviceGuid/plans/$planGuid/schemas/$defaultServiceReference/$defaultMethod".toString()

    void setup() {
        serviceRepository = Mock(CFServiceRepository)
    }

    private SchemaFormService createService(SchemaFormServiceConfiguration config = null) {
        if (config == null) {
            config = new SchemaFormServiceConfiguration(
                    hostname: "localhost",
                    port: 80,
                    protocol: "http"
            )
        }

        return new SchemaFormService(serviceRepository, config)
    }

    void "Schema with Id is parsed correctly"() {
        setup:
        def sut = createService()
        def schema = '{"id":"someId","properties":{"field1":{"type":"string"}}}'

        when:
        def preparedSchema = sut.serializeAndPrepareSchema(
                JsonHelper.parse(schema, java.lang.Object),
                serviceGuid,
                planGuid,
                defaultServiceReference,
                defaultMethod)

        then:
        def jsonSchema = new ObjectMapper().readTree(preparedSchema)
        String jsonSchemaId = jsonSchema.get("id").textValue()
        jsonSchemaId == expectedId
    }

    void "Schema without Id is parsed correctly"() {
        setup:
        def sut = createService()
        def schema = '{"properties":{"field1":{"type":"string"}}}'

        when:
        def preparedSchema = sut.serializeAndPrepareSchema(
                JsonHelper.parse(schema, java.lang.Object),
                serviceGuid,
                planGuid,
                defaultServiceReference,
                defaultMethod)

        then:
        def jsonSchema = new ObjectMapper().readTree(preparedSchema)
        String jsonSchemaId = jsonSchema.get("id").textValue()
        jsonSchemaId == expectedId
    }
}
