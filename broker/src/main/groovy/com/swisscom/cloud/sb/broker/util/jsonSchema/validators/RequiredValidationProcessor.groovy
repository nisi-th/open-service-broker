package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchema

class RequiredValidationProcessor implements ValidationProcessor {

    private final JsonSchema jsonSchema

    RequiredValidationProcessor(JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema
    }

    @Override
    List<ValidationError> validate(Map object) {
        List<ValidationError> errors = new ArrayList<ValidationError>()

        for (String requiredProperty in this.jsonSchema.required) {
            if (!object.containsKey(requiredProperty)) {
                errors.add(createPropertyDoesNotExist(requiredProperty))
                continue
            }

            if (isNullOrEmpty(object.get(requiredProperty))) {
                errors.add(createPropertyIsNullOrEmpty(requiredProperty))
            }
        }

        return errors
    }

    boolean isNullOrEmpty(def propertyValue) {
        if (propertyValue == null)
            return true

        if (propertyValue instanceof String)
            return !propertyValue

        if (propertyValue instanceof ArrayList)
            return !(propertyValue as ArrayList).any()

        false
    }

    ValidationError createPropertyDoesNotExist(String propertyName) {
        new ValidationError(
                propertyPath: propertyName,
                error: "the required property '$propertyName' did not exist.".toString(),
                ValidationProcessor: this.class.typeName)
    }

    ValidationError createPropertyIsNullOrEmpty(String propertyName) {
        new ValidationError(
                propertyPath: propertyName,
                error: "the required property '$propertyName' was null or empty.".toString(),
                ValidationProcessor: this.class.typeName)
    }
}
