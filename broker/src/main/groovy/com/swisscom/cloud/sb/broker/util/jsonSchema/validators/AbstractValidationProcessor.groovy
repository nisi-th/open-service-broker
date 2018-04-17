package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchema
import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchemaProperty

abstract class AbstractValidationProcessor implements ValidationProcessor {
    protected final JsonSchema jsonSchema

    protected AbstractValidationProcessor(JsonSchema jsonSchema)
    {
        this.jsonSchema = jsonSchema
    }

    @Override
    List<ValidationError> validate(Map object) {
        List<ValidationError> errors = new ArrayList<ValidationError>()

        for (def property in jsonSchema.properties) {
            errors.add(validateProperty(property.key, property.value, object.get(property.key)))
        }

        errors
    }

    protected abstract ValidationError validateProperty(String propertyName, JsonSchemaProperty jsonSchemaProperty, objectProperty)
}
