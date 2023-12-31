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
package com.alipay.sofa.registry.server.session.remoting.handler;

import com.alipay.sofa.registry.common.model.Node;
import com.alipay.sofa.registry.remoting.Channel;
import com.alipay.sofa.registry.server.session.bootstrap.ExecutorManager;
import com.alipay.sofa.registry.server.shared.remoting.AbstractServerHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;

public abstract class AbstractClientDataRequestHandler<T> extends AbstractServerHandler<T> {
    @Autowired
    protected ExecutorManager executorManager;

    @Override
    protected Node.NodeType getConnectNodeType() {
        return Node.NodeType.CLIENT;
    }

    @Override
    public Executor getExecutor() {
        return executorManager.getAccessDataExecutor();
    }

    @Override
    public InvokeType getInvokeType() {
        return InvokeType.SYNC;
    }

    @Override
    protected void logRequest(Channel channel, T request) {
        // not log
    }
}
