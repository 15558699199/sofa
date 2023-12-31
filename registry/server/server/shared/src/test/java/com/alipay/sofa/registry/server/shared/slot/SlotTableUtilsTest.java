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
package com.alipay.sofa.registry.server.shared.slot;

import com.alipay.sofa.registry.common.model.slot.Slot;
import com.alipay.sofa.registry.common.model.slot.SlotTable;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class SlotTableUtilsTest {
    @Test
    public void test() {
        SlotTable slotTable =
                new SlotTable(10, Lists.newArrayList(new Slot(1, null, 0, Collections.emptyList())));
        Assert.assertFalse(SlotTableUtils.checkNoLeaderEmpty(slotTable));
        Assert.assertFalse(SlotTableUtils.isValidSlotTable(slotTable));

        slotTable =
                new SlotTable(10, Lists.newArrayList(new Slot(1, "test", 0, Lists.newArrayList("test"))));
        Assert.assertFalse(SlotTableUtils.checkNoDupLeaderAndFollowers(slotTable));
        Assert.assertFalse(SlotTableUtils.isValidSlotTable(slotTable));
    }
}
