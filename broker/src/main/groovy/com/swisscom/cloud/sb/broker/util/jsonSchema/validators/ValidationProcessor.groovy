package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

interface ValidationProcessor {
    List<ValidationError> validate(Map object)
}
