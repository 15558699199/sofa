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
package com.alipay.sofa.registry.server.data.remoting;

import com.alipay.sofa.registry.remoting.ChannelHandler;
import com.alipay.sofa.registry.remoting.exchange.Exchange;
import com.alipay.sofa.registry.server.data.bootstrap.DataServerConfig;
import com.alipay.sofa.registry.server.shared.remoting.ClientSideExchanger;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;

/**
 * @author xuanbei
 * @since 2019/2/15
 */
public class DataNodeExchanger extends ClientSideExchanger {

    @Autowired
    private DataServerConfig dataServerConfig;

    public DataNodeExchanger() {
        super(Exchange.DATA_SERVER_TYPE);
    }

    @Override
    public int getRpcTimeoutMillis() {
        return dataServerConfig.getRpcTimeoutMillis();
    }

    @Override
    public int getServerPort() {
        // the Exchanger only for sync data
        return dataServerConfig.getSyncDataPort();
    }

    @Override
    public int getConnNum() {
        return dataServerConfig.getSyncDataConnNum();
    }

    @Override
    protected Collection<ChannelHandler> getClientHandlers() {
        return Collections.emptyList();
    }

    @VisibleForTesting
    void setDataServerConfig(DataServerConfig dataServerConfig) {
        this.dataServerConfig = dataServerConfig;
    }
}
