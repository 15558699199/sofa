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
package com.alipay.sofa.registry.server.shared.comparator;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ComparatorTest {
  @Test
  public void test() {
    Set<String> prev = Sets.newHashSet("1", "2");
    Set<String> cur = Sets.newHashSet("2", "3");

    NodeComparator comparator = new NodeComparator(prev, cur);
    Assert.assertEquals(comparator.totalChange(), 2);
    Assert.assertTrue(comparator.hasAnyChange());
    Assert.assertEquals(comparator.getAdded(), Sets.newHashSet("3"));
    Assert.assertEquals(comparator.getRemoved(), Sets.newHashSet("1"));
  }
}
