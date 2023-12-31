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
package com.alipay.sofa.registry.server.session.providedata;

import com.alipay.sofa.registry.server.session.TestUtils;
import com.alipay.sofa.registry.server.session.bootstrap.SessionServerConfigBean;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ConfigProvideDataWatcherTest {

    private ConfigProvideDataWatcher newWatcher() {
        ConfigProvideDataWatcher watcher = new ConfigProvideDataWatcher();
        SessionServerConfigBean bean = TestUtils.newSessionConfig("testDc");
        watcher.sessionServerConfig = bean;
        Assert.assertEquals(watcher.fetchBatchSize(), bean.getWatchConfigFetchBatchSize());
        Assert.assertEquals(watcher.fetchIntervalMillis(), bean.getWatchConfigFetchIntervalMillis());
        Assert.assertEquals(watcher.watcherLeaseSecs(), bean.getWatchConfigFetchLeaseSecs());
        return watcher;
    }

    @Test
    public void test() {
        ConfigProvideDataWatcher watcher = newWatcher();

        String dataId = "testDataId";
        Assert.assertNull(watcher.get(dataId));

        Assert.assertTrue(watcher.watch(dataId));
        Assert.assertNull(watcher.get(dataId));

        Assert.assertTrue(watcher.watch(dataId));
        Assert.assertEquals(1, watcher.refreshWatch(Collections.singletonList(dataId)));
    }
}
