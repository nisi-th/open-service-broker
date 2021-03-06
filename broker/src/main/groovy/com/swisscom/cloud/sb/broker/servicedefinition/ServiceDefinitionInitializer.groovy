/*
 * Copyright (c) 2018 Swisscom (Switzerland) Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.swisscom.cloud.sb.broker.servicedefinition

import com.swisscom.cloud.sb.broker.model.CFService
import com.swisscom.cloud.sb.broker.model.repository.CFServiceRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
@EnableConfigurationProperties
@Slf4j
class ServiceDefinitionInitializer {

    private CFServiceRepository cfServiceRepository

    private ServiceDefinitionConfig serviceDefinitionConfig

    private ServiceDefinitionProcessor serviceDefinitionProcessor

    @Autowired
    ServiceDefinitionInitializer(CFServiceRepository cfServiceRepository, ServiceDefinitionConfig serviceDefinitionConfig, ServiceDefinitionProcessor serviceDefinitionProcessor) {
        this.cfServiceRepository = cfServiceRepository
        this.serviceDefinitionConfig = serviceDefinitionConfig
        this.serviceDefinitionProcessor = serviceDefinitionProcessor
    }

    @PostConstruct
    void init() throws Exception {
        List<CFService> cfServiceList = cfServiceRepository.findAll()

        checkForMissingServiceDefinitions(cfServiceList)
        addServiceDefinitions()
    }

    void checkForMissingServiceDefinitions(List<CFService> cfServiceList) {
        def configGuidList = serviceDefinitionConfig.serviceDefinitions.collect {it.guid}

        def guidList = cfServiceList.collect {it.guid}

        if (configGuidList.size() != 0) {
            if (!configGuidList.containsAll(guidList)) {
                throw new RuntimeException("Missing service definition configuration exception. Service list - ${guidList}")
            }
        }
    }

    void addServiceDefinitions() {
        serviceDefinitionConfig.serviceDefinitions.each {
            serviceDefinitionProcessor.createOrUpdateServiceDefinitionFromYaml(it)
        }
    }
}
