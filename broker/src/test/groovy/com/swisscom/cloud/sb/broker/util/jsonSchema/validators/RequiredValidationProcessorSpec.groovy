package com.swisscom.cloud.sb.broker.util.jsonSchema.validators

class RequiredValidationProcessorSpec extends BaseValidationProcessorSpec {

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

    private RequiredValidationProcessor getProcessor() {
        new RequiredValidationProcessor(deserializeJsonSchema(jsonSchema))
    }

    def "No ValidationErrors when all fields are present"() {
        given:
        def data = '{"name":"Hannes","size":17,"list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        !validationErrors.any()
    }

    def "ValidationError when Name is missing"() {
        given:
        def data = '{"size":17,"list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when Name is empty"() {
        given:
        def data = '{"name":"","size":17,"list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when Name is null"() {
        given:
        def data = '{"name":null,"size":17,"list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when Number is null"() {
        given:
        def data = '{"name":"Hannes","size":null,"list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when Number is missing"() {
        given:
        def data = '{"name":"Hannes","list":["a", 1, "c"], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when List is missing"() {
        given:
        def data = '{"name":"Hannes","size":17, "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when List is null"() {
        given:
        def data = '{"name":"Hannes","size":17,"list":null, "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }

    def "ValidationError when List is empty"() {
        given:
        def data = '{"name":"Hannes","size":17,"list":[], "isAwesome": true}'
        when:
        def sut = getProcessor()
        def validationErrors = sut.validate(deserializeObject(data))

        then:
        noExceptionThrown()
        validationErrors.any()
    }
}
