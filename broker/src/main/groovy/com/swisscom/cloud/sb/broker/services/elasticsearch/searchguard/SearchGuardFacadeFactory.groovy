package com.swisscom.cloud.sb.broker.services.elasticsearch.searchguard

import com.swisscom.cloud.sb.broker.model.repository.ServiceInstanceRepository
import com.swisscom.cloud.sb.broker.services.elasticsearch.ElasticSearchConfig
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@CompileStatic
@Component
@Slf4j
class SearchGuardFacadeFactory {
    private final SearchGuardClientFactory searchGuardClientFactory
    private final ElasticSearchConfig elasticSearchConfig
    private final ServiceInstanceRepository serviceInstanceRepository

    @Autowired
    SearchGuardFacadeFactory(SearchGuardClientFactory searchGuardClientFactory, ElasticSearchConfig elasticSearchConfig, ServiceInstanceRepository serviceInstanceRepository) {
        this.searchGuardClientFactory = searchGuardClientFactory
        this.elasticSearchConfig = elasticSearchConfig
        this.serviceInstanceRepository = serviceInstanceRepository
    }

    SearchGuardFacade build(List<String> hosts, int port) {
        if (elasticSearchConfig.useLocalhostForTesting) {
            log.info("Using localhost:9201 as Elastic host")
            return new SearchGuardFacade(searchGuardClientFactory, elasticSearchConfig, serviceInstanceRepository , ["localhost"], 9201)
        } else {
            log.debug("Create new SearchGuardFacade on ${hosts}:{$port}")
            return new SearchGuardFacade(searchGuardClientFactory, elasticSearchConfig, serviceInstanceRepository , hosts, port)

        }
    }
}
