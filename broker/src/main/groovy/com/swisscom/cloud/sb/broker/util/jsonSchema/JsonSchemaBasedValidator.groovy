package com.swisscom.cloud.sb.broker.util.jsonSchema

import com.fasterxml.jackson.databind.ObjectMapper
import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchema
import com.swisscom.cloud.sb.broker.util.jsonSchema.validators.RequiredValidationProcessor
import com.swisscom.cloud.sb.broker.util.jsonSchema.validators.ValidationError
import com.swisscom.cloud.sb.broker.util.jsonSchema.validators.ValidationProcessor

import javax.management.RuntimeErrorException

class JsonSchemaBasedValidator {

    private final JsonSchema jsonSchema

    static JsonSchemaBasedValidator create(String json) {
        def jsonSchema = new ObjectMapper().readValue(json, JsonSchema.class)

        return new JsonSchemaBasedValidator(jsonSchema)
    }

    private JsonSchemaBasedValidator(JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema
    }

    void assertIsValid(Map object) {
        if (validate(object).any())
            throw new RuntimeErrorException("Validation Exception")
    }

    List<ValidationError> validate(Map object) {
        getValidationProcessors().forEach { vP ->
            def errors = vP.validate(object)
            if (errors.any())
                return errors
        }

        []
    }

    private List<ValidationProcessor> getValidationProcessors() {
        [
                new RequiredValidationProcessor(this.jsonSchema)
        ]
    }


}
