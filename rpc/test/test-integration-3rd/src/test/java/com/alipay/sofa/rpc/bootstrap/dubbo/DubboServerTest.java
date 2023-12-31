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
package com.alipay.sofa.rpc.bootstrap.dubbo;

import com.alipay.sofa.rpc.bootstrap.ConsumerBootstrap;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.common.SystemInfo;
import com.alipay.sofa.rpc.config.*;
import com.alipay.sofa.rpc.registry.base.BaseZkTest;
import com.alipay.sofa.rpc.test.HelloService;
import com.alipay.sofa.rpc.test.HelloServiceImpl;
import org.apache.dubbo.config.ConfigKeys;
import org.apache.dubbo.config.context.ConfigMode;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href=mailto:leizhiyuan@gmail.com>leizhiyuan</a>
 */
public class DubboServerTest extends BaseZkTest {

    private static String OLD_VALUE_DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE;
    private static String OLD_VALUE_DUBBO_CONFIG_MODE;

    @BeforeClass
    public static void beforeClass() {
        OLD_VALUE_DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE = System
                .getProperty(ConfigKeys.DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE);
        OLD_VALUE_DUBBO_CONFIG_MODE = System.getProperty(ConfigKeys.DUBBO_CONFIG_MODE);
        System.setProperty(ConfigKeys.DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE, "true");
        System.setProperty(ConfigKeys.DUBBO_CONFIG_MODE, ConfigMode.IGNORE.name());
    }

    @AfterClass
    public static void afterClass() {
        if (OLD_VALUE_DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE != null) {
            System.setProperty(ConfigKeys.DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE,
                    OLD_VALUE_DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE);
        } else {
            System.clearProperty(ConfigKeys.DUBBO_CONFIG_IGNORE_DUPLICATED_INTERFACE);
        }

        if (OLD_VALUE_DUBBO_CONFIG_MODE != null) {
            System.setProperty(ConfigKeys.DUBBO_CONFIG_MODE, OLD_VALUE_DUBBO_CONFIG_MODE);
        } else {
            System.clearProperty(ConfigKeys.DUBBO_CONFIG_MODE);
        }
    }

    @Test
    //同步调用,走服务注册中心
    public void testRegistrySync() {
        // 只有1个线程 执行
        ServerConfig serverConfig = new ServerConfig()
                .setStopTimeout(10)
                .setPort(20880)
                .setProtocol("dubbo")
                .setQueues(100).setCoreThreads(1).setMaxThreads(2).setHost(SystemInfo.getLocalHost());

        // 发布一个服务，每个请求要执行1秒
        ApplicationConfig serverApplacation = new ApplicationConfig();
        serverApplacation.setAppName("server");
        List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
        RegistryConfig registryConfig;

        registryConfig = new RegistryConfig()
                .setProtocol(RpcConstants.REGISTRY_PROTOCOL_ZK)
                .setAddress("127.0.0.1:2181")
                .setSubscribe(true)
                .setRegister(true);

        List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>();
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setTimeout(3000);
        methodConfig.setName("sayHello");
        methodConfigs.add(methodConfig);

        registryConfigs.add(registryConfig);
        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl())
                .setServer(serverConfig)
                //.setParameter(RpcConstants.CONFIG_HIDDEN_KEY_WARNING, "false")
                .setRegister(true)
                .setBootstrap("dubbo")
                .setRegistry(registryConfigs)
                .setApplication(serverApplacation);
        providerConfig.export();

        ApplicationConfig clientApplication = new ApplicationConfig();
        clientApplication.setAppName("client");
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setTimeout(30000)
                .setRegister(true)
                .setProtocol("dubbo")
                .setApplication(clientApplication)
                .setRegistry(registryConfigs)
                .setMethods(methodConfigs)
                .setInJVM(false);
        final HelloService demoService = consumerConfig.refer();

        String result = demoService.sayHello("xxx", 22);
        Assert.assertNotNull(result);

        ConsumerBootstrap bootstrap = consumerConfig.getConsumerBootstrap();
        Assert.assertTrue(bootstrap instanceof DubboConsumerBootstrap);
        Assert.assertTrue(bootstrap.isSubscribed());
        Assert.assertNotNull(bootstrap.getProxyIns());
        bootstrap.unRefer();
        try {
            bootstrap.getCluster();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
        try {
            bootstrap.subscribe();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }
}