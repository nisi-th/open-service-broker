package com.swisscom.cloud.sb.broker.services.common

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "com.swisscom.cloud.sb.broker.jsonSchema")
class SchemaFormServiceConfiguration {
    String hostname
    String protocol
    String port
}
