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
package com.alipay.sofa.boot.tracer.flexible;

import com.alipay.common.tracer.core.span.CommonSpanTags;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.alipay.common.tracer.core.utils.StringUtils;
import com.alipay.sofa.tracer.plugin.flexible.FlexibleTracer;
import com.alipay.sofa.tracer.plugin.flexible.annotations.Tracer;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Implementation of {@link MethodInvocationProcessor} to generate tracer spanner around method invoke.
 *
 * @author guolei.sgl (guolei.sgl@antfin.com) 2019/8/9 2:51 PM
 * @author huzijie
 **/
public class SofaTracerMethodInvocationProcessor implements MethodInvocationProcessor {

    private final io.opentracing.Tracer tracer;

    public SofaTracerMethodInvocationProcessor(io.opentracing.Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Object process(MethodInvocation invocation, Tracer tracerSpan) throws Throwable {
        return proceedProxyMethodWithTracerAnnotation(invocation, tracerSpan);
    }

    private Object proceedProxyMethodWithTracerAnnotation(MethodInvocation invocation,
                                                          Tracer tracerSpan) throws Throwable {
        if (tracer instanceof FlexibleTracer) {
            try {
                String operationName = tracerSpan.operateName();
                if (StringUtils.isBlank(operationName)) {
                    operationName = invocation.getMethod().getName();
                }
                SofaTracerSpan sofaTracerSpan = ((FlexibleTracer) tracer)
                    .beforeInvoke(operationName);
                sofaTracerSpan.setTag(CommonSpanTags.METHOD, invocation.getMethod().getName());
                if (invocation.getArguments().length != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Object obj : invocation.getArguments()) {
                        stringBuilder.append(obj.getClass().getName()).append(";");
                    }
                    sofaTracerSpan.setTag("param.types",
                        stringBuilder.substring(0, stringBuilder.length() - 1));
                }
                Object result = invocation.proceed();
                ((FlexibleTracer) tracer).afterInvoke();
                return result;
            } catch (Throwable t) {
                ((FlexibleTracer) tracer).afterInvoke(t.getMessage());
                throw t;
            }
        } else {
            return invocation.proceed();
        }
    }

}
