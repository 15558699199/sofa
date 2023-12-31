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
package com.alipay.sofa.boot.autoconfigure.tracer.flexible;

import com.alipay.common.tracer.core.reporter.facade.Reporter;
import com.alipay.common.tracer.core.samplers.Sampler;
import com.alipay.common.tracer.core.samplers.SamplerFactory;
import com.alipay.sofa.boot.autoconfigure.tracer.SofaTracerProperties;
import com.alipay.sofa.boot.tracer.flexible.MethodInvocationProcessor;
import com.alipay.sofa.boot.tracer.flexible.SofaTracerAdvisingBeanPostProcessor;
import com.alipay.sofa.boot.tracer.flexible.SofaTracerIntroductionInterceptor;
import com.alipay.sofa.boot.tracer.flexible.SofaTracerMethodInvocationProcessor;
import com.alipay.sofa.tracer.plugin.flexible.FlexibleTracer;
import io.opentracing.Tracer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Flexible.
 *
 * @author guolei.sgl (guolei.sgl@antfin.com) 2019/8/9 3:20 PM
 * @author huzijie
 **/
@AutoConfiguration
@ConditionalOnClass({ Tracer.class,
                     com.alipay.sofa.tracer.plugin.flexible.annotations.Tracer.class,
                     SofaTracerIntroductionInterceptor.class })
@ConditionalOnBean(SofaTracerProperties.class)
@ConditionalOnProperty(name = "sofa.boot.tracer.flexible.enabled", havingValue = "true", matchIfMissing = true)
public class FlexibleAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Tracer sofaTracer(ObjectProvider<SofaTracerProperties> sofaTracerPropertiesObjectProvider)
                                                                                                     throws Exception {
        SofaTracerProperties sofaTracerProperties = sofaTracerPropertiesObjectProvider
            .getIfUnique();
        String reporterName = null;
        if (sofaTracerProperties != null) {
            reporterName = sofaTracerProperties.getReporterName();
        }
        if (StringUtils.hasText(reporterName)) {
            Reporter reporter = (Reporter) Class.forName(reporterName).getDeclaredConstructor()
                .newInstance();
            Sampler sampler = SamplerFactory.getSampler();
            return new FlexibleTracer(sampler, reporter);
        }
        return new FlexibleTracer();
    }

    @Bean
    @ConditionalOnMissingBean
    public MethodInvocationProcessor sofaMethodInvocationProcessor(Tracer tracer) {
        return new SofaTracerMethodInvocationProcessor(tracer);
    }

    @Bean
    @ConditionalOnMissingBean
    public SofaTracerIntroductionInterceptor sofaTracerIntroductionInterceptor(MethodInvocationProcessor methodInvocationProcessor) {
        return new SofaTracerIntroductionInterceptor(methodInvocationProcessor);
    }

    @Bean
    @ConditionalOnMissingBean
    public SofaTracerAdvisingBeanPostProcessor tracerAnnotationBeanPostProcessor(SofaTracerIntroductionInterceptor methodInterceptor) {
        return new SofaTracerAdvisingBeanPostProcessor(methodInterceptor);
    }
}
