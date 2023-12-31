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
package com.alipay.sofa.registry.server.meta.cluster.node;

import com.alipay.sofa.registry.server.meta.AbstractMetaServerTestBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhuchen
 * @date Dec 15, 2020, 8:02:29 PM
 * <p>nothing here, simply increase unit test coverage
 */
public class NodeModifiedTest extends AbstractMetaServerTestBase {

    @Test
    public void testGetOldNode() {
        NodeModified<SimpleNode> event =
                new NodeModified<>(new SimpleNode(randomIp()), new SimpleNode(randomIp()));
        Assert.assertNotNull(event.getNewNode());
        Assert.assertNotNull(event.getOldNode());
        Assert.assertNotEquals(event.getOldNode(), event.getNewNode());
    }
}
