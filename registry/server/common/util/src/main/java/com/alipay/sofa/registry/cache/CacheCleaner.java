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
package com.alipay.sofa.registry.cache;

import com.alipay.sofa.registry.util.NamedThreadFactory;
import com.google.common.cache.Cache;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CacheCleaner {
    private static final CacheCleaner cleaner = new CacheCleaner();
    private final ScheduledExecutorService executorService =
            new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("CacheCleaner"));

    public static void autoClean(Cache cache, long intervalMs) {
        cleaner.executorService.scheduleAtFixedRate(
                cache::cleanUp, intervalMs, intervalMs, TimeUnit.MILLISECONDS);
    }
}
