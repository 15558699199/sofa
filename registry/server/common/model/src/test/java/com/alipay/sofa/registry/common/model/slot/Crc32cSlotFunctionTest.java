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
package com.alipay.sofa.registry.common.model.slot;

import com.alipay.sofa.registry.common.model.slot.func.Crc32cSlotFunction;
import org.junit.Test;

import java.util.Date;

public class Crc32cSlotFunctionTest {
    @Test
    public void test() {
        Crc32cSlotFunction function = new Crc32cSlotFunction(256);
        System.out.println(function.slotOf("aaaaaaaaaa"));

        System.out.println(new Date(1624591989649L));
    }
}
