package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

import com.fasterxml.jackson.databind.ObjectMapper
import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchema
import spock.lang.Specification

abstract class BaseValidationProcessorSpec extends Specification {
    private final objectMapper = new ObjectMapper()

    JsonSchema deserializeJsonSchema(String jsonSchema) {
        objectMapper.readValue(jsonSchema, JsonSchema.class)
    }

    Map deserializeObject(String json) {
        objectMapper.readValue(json, Map.class)
    }

}
