package com.swisscom.cloud.sb.broker.util.jsonSchema.models

class JsonSchema {
    String title
    String type
    Map<String, JsonSchemaProperty> properties
    String[] required
}
