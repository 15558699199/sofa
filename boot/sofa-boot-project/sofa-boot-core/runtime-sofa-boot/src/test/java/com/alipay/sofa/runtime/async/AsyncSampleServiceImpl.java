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
package com.alipay.sofa.runtime.async;

import com.alipay.sofa.runtime.sample.SampleService;

/**
 * @author huzijie
 * @version AsyncSampleServiceImpl.java, v 0.1 2023年04月10日 10:54 AM huzijie Exp $
 */
public class AsyncSampleServiceImpl implements SampleService {

    @Override
    public String service() {
        Thread thread = Thread.currentThread();
        return thread.getName();
    }

    @Override
    public String test() {
        Thread thread = Thread.currentThread();
        return thread.getName();
    }
}
