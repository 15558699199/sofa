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
package com.alipay.sofa.boot.autoconfigure.tracer.redis;

import com.sofa.alipay.tracer.plugins.spring.redis.SofaTracerRCFBeanPostProcessor;
import com.sofa.alipay.tracer.plugins.spring.redis.common.RedisActionWrapperHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RedisAutoConfiguration}.
 *
 * @author huzijie
 * @version RedisAutoConfigurationTests.java, v 0.1 2023年01月11日 10:38 AM huzijie Exp $
 */
public class RedisAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations
                    .of(RedisAutoConfiguration.class));

    @Test
    public void registerRedisBeans() {
        this.contextRunner
                .run((context) -> assertThat(context)
                        .hasSingleBean(RedisActionWrapperHelper.class)
                        .hasSingleBean(SofaTracerRCFBeanPostProcessor.class));
    }

    @Test
    public void noRedisBeansWhenRedisConnectionFactoryClassNotExist() {
        this.contextRunner.withClassLoader(new FilteredClassLoader(RedisConnectionFactory.class))
                .run((context) -> assertThat(context)
                        .doesNotHaveBean(RedisActionWrapperHelper.class)
                        .doesNotHaveBean(SofaTracerRCFBeanPostProcessor.class));
    }

    @Test
    public void noRedisBeansWhenRedisActionWrapperHelperClassNotExist() {
        this.contextRunner.withClassLoader(new FilteredClassLoader(RedisActionWrapperHelper.class))
                .run((context) -> assertThat(context)
                        .doesNotHaveBean(RedisActionWrapperHelper.class)
                        .doesNotHaveBean(SofaTracerRCFBeanPostProcessor.class));
    }

    @Test
    public void noRedisBeansWhenPropertySetFalse() {
        this.contextRunner
                .withPropertyValues("sofa.boot.tracer.redis.enabled=false")
                .run((context) -> assertThat(context)
                        .doesNotHaveBean(RedisActionWrapperHelper.class)
                        .doesNotHaveBean(SofaTracerRCFBeanPostProcessor.class));
    }
}
