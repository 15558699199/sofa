/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.boot.autoconfigure.rpc;

import com.alipay.sofa.boot.autoconfigure.condition.ConditionalOnSwitch;
import com.alipay.sofa.rpc.boot.config.MeshConfigurator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mesh Registry configuration
 * @author yuanxuan
 * @version : MeshRegistryConfiguration.java, v 0.1 2023年01月31日 17:10 yuanxuan Exp $
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSwitch(value = "rpcMeshRegistry")
public class MeshRegistryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MeshConfigurator meshConfigurator() {
        return new MeshConfigurator();
    }
}
