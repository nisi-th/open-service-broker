package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchema
import com.swisscom.cloud.sb.broker.util.jsonSchema.models.JsonSchemaProperty

class TypeValidationProcessor extends AbstractValidationProcessor {

    protected TypeValidationProcessor(JsonSchema jsonSchema) {
        super(jsonSchema)
    }

    @Override
    protected ValidationError validateProperty(String propertyName, JsonSchemaProperty jsonSchemaProperty, Object objectProperty) {
        String expectedType = jsonSchemaProperty.type

        switch (expectedType) {
            case "string":

            break;
            case "number":

                break;
            case "object":

                break;
            case "array":

                break;
            case "boolean":

                break;
            default:
                return createInvalidType(propertyName, expectedType)
        }

        return null;
    }


    ValidationError createTypeMismatchError(String propertyName, String expectedType, String realType) {
        new ValidationError(
                propertyPath: propertyName,
                error: "the required property '$propertyName' did not exist.".toString(),
                ValidationProcessor: this.class.typeName)
    }

    ValidationError createInvalidType(String propertyName, String expectedType) {
        new ValidationError(
                propertyPath: propertyName,
                error: "the '$propertyName' defines a type '$expectedType'.".toString(),
                ValidationProcessor: this.class.typeName)
    }
}
