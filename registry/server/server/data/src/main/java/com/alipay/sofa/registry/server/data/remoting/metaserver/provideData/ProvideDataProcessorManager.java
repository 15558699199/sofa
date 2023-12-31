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
package com.alipay.sofa.registry.server.data.remoting.metaserver.provideData;

import com.alipay.sofa.registry.common.model.metaserver.ProvideData;
import com.alipay.sofa.registry.server.shared.providedata.ProvideDataProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kezhu.wukz
 * @version 1.0: ProvideDataProcessorManager.java, v 0.1 2019-12-25 17:39 kezhu.wukz Exp $
 */
public class ProvideDataProcessorManager implements ProvideDataProcessor {

    private final List<ProvideDataProcessor> provideDataProcessors = new ArrayList<>();

    public void addProvideDataProcessor(ProvideDataProcessor provideDataProcessor) {
        provideDataProcessors.add(provideDataProcessor);
    }

    @Override
    public boolean processData(ProvideData provideData) {
        for (ProvideDataProcessor provideDataProcessor : provideDataProcessors) {
            if (provideDataProcessor.support(provideData.getDataInfoId())) {
                provideDataProcessor.processData(provideData);
            }
        }

        return true;
    }

    @Override
    public boolean support(String dataInfoId) {
        return false;
    }
}
