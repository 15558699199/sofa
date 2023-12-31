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
package com.alipay.sofa.rpc.codec.bolt;

import com.alipay.remoting.CustomSerializerManager;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extension;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Register custom serializer to bolt.
 *
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
@Extension(value = "sofaRpcSerializationRegister")
public class SofaRpcSerializationRegister extends AbstractSerializationRegister {

    private final SofaRpcSerialization sofaRpcSerialization = new SofaRpcSerialization();

    private volatile AtomicBoolean registered = new AtomicBoolean(false);

    @Override
    public void doRegisterCustomSerializer() {
        if (registered.compareAndSet(false, true)) {
            innerRegisterCustomSerializer();
        }
    }

    /**
     * we can override or rewrite the method
     */
    protected void innerRegisterCustomSerializer() {
        // 注册序列化器到bolt
        if (CustomSerializerManager.getCustomSerializer(SofaRequest.class.getName()) == null) {
            CustomSerializerManager.registerCustomSerializer(SofaRequest.class.getName(),
                    sofaRpcSerialization);
        }
        if (CustomSerializerManager.getCustomSerializer(SofaResponse.class.getName()) == null) {
            CustomSerializerManager.registerCustomSerializer(SofaResponse.class.getName(),
                    sofaRpcSerialization);
        }
    }
}
