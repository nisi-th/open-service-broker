package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchema

class AbstractValidationProcessor implements ValidationProcessor {
    protected final JsonSchema jsonSchema

    protected AbstractValidationProcessor(JsonSchema jsonSchema)
    {
        this.jsonSchema = jsonSchema
    }

    @Override
    List<ValidationError> validate(Map object) {
        return null
    }
}
