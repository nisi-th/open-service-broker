package com.swisscom.cloud.sb.broker.util.jsonSchema

import com.swisscom.cloud.sb.broker.util.jsonSchema.validators.BaseValidationProcessorSpec

class JsonSchemaBasedValidatorSpec extends BaseValidationProcessorSpec {

    final String jsonSchema = """{
        "title": "RequiredValidationProcessorSpec",
        "properties": {
            "name": { 
                "type": "string"
            },
            "size": {
                "type": "number"
            },
            "list": {
                "type": "array"
            },
            "isAwesome": {
                "type": "boolean"   
            }
        },
        "required": [
            "name",
            "size",
            "list",
            "isAwesome"        
        ]        
    }"""

    def "Can create a validator based from valid jsonSchema"() {
        given:
        String jsonSchema = '{"title":"example","properties":{"address":{"type":"string"}}}'

        when:
        def validator = JsonSchemaBasedValidator.create(jsonSchema)

        then:
        noExceptionThrown()
    }

    def "Invalid jsonSchema cannot be created"() {
        given:
        String jsonSchema = 'randomText'

        when:
        def validator = JsonSchemaBasedValidator.create(jsonSchema)

        then:
        def exception = thrown(Exception)
    }

    def "Validation is being processed when correct"() {
        given:
        def data = '{"name":"Hannes","size":17,"list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = JsonSchemaBasedValidator.create(jsonSchema)
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        !validationErrors.any()
    }

    def "Validation is being processed when incorrect"() {
        given:
        def data = '{"name":"Hannes","list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = JsonSchemaBasedValidator.create(jsonSchema)
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        !validationErrors.any()
    }
}
