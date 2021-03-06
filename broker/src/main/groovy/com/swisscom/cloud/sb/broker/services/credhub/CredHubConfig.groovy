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

package com.swisscom.cloud.sb.broker.services.credhub

import com.swisscom.cloud.sb.broker.config.Config
import groovy.transform.CompileStatic
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails

/*
Defines the config needed for defining a CredHub datasource
 */
@CompileStatic
trait CredHubConfig implements Config {
    boolean enable
    String url
    ResourceOwnerPasswordResourceDetails oauth2_resourceowner
}