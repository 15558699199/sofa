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
package com.alipay.sofa.registry.client.remoting;

import com.alipay.sofa.registry.client.api.Subscriber;
import com.alipay.sofa.registry.client.provider.DefaultSubscriber;
import com.alipay.sofa.registry.client.provider.RegisterCache;
import com.alipay.sofa.registry.client.task.ObserverHandler;
import com.alipay.sofa.registry.core.model.DataBox;
import com.alipay.sofa.registry.core.model.ReceivedData;
import com.alipay.sofa.registry.core.model.Result;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
public class ReceivedDataProcessorTest {

    private static ReceivedDataProcessor processor;

    @BeforeClass
    public static void init() {
        RegisterCache registerCache = mock(RegisterCache.class);
        ObserverHandler handler = mock(ObserverHandler.class);
        Subscriber configurator2 = mock(Subscriber.class);
        when(registerCache.getSubscriberByRegistId("11")).thenReturn(null);
        when(registerCache.getSubscriberByRegistId("22")).thenReturn(configurator2);
        DefaultSubscriber configurator3 = mock(DefaultSubscriber.class);
        when(registerCache.getSubscriberByRegistId("33")).thenReturn(configurator3);
        doThrow(new RuntimeException()).when(handler).notify(configurator3);

        processor = new ReceivedDataProcessor(registerCache, handler);
    }

    @Test
    public void handleRequest() {
        Result result = (Result) processor.handleRequest(null, null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNull(result.getMessage());

        ReceivedData request = new ReceivedData();
        result = (Result) processor.handleRequest(null, request);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNull(result.getMessage());

        request = new ReceivedData();
        request.setSubscriberRegistIds(null);
        request.setData(new HashMap<String, List<DataBox>>());
        request.setVersion(1234L);
        request.setSegment("seg1");
        request.setLocalZone("local");

        result = (Result) processor.handleRequest(null, request);
        Assert.assertFalse(result.isSuccess());
        Assert.assertEquals("", result.getMessage());

        request.setSubscriberRegistIds(Arrays.asList("11", "22", "33"));
        result = (Result) processor.handleRequest(null, request);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNull(result.getMessage());
    }

    @Test
    public void interest() {
        Assert.assertEquals(ReceivedData.class.getName(), processor.interest());
    }
}
