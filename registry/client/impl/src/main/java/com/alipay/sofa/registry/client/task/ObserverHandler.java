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
package com.alipay.sofa.registry.client.task;

import com.alipay.sofa.registry.client.api.Configurator;
import com.alipay.sofa.registry.client.api.Subscriber;

/**
 * The interface Observer handler.
 *
 * @author zhuoyu.sjw
 * @version $Id : ObserverHandler.java, v 0.1 2018-03-15 12:15 zhuoyu.sjw Exp $$
 */
public interface ObserverHandler {

    /**
     * Notify.
     *
     * @param subscriber the subscriber
     */
    void notify(Subscriber subscriber);

    /**
     * Notify.
     *
     * @param configurator the configurator
     */
    void notify(Configurator configurator);
}
