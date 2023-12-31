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
package com.alipay.sofa.registry.jraft.revision;

import com.alipay.sofa.registry.common.model.store.AppRevision;
import com.alipay.sofa.registry.log.Logger;
import com.alipay.sofa.registry.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AppRevisionRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRevisionService.class);
    @Autowired
    private AppRevisionService appRevisionService;

    public void register(AppRevision appRevision) {
        if (appRevisionService.existed(appRevision.getRevision())) {
            return;
        }
        appRevisionService.add(appRevision);
        LOGGER.info("register new revision: {}", appRevision.getRevision());
    }

    public List<String> checkRevisions(String keysDigest) {
        if (keysDigest.equals(appRevisionService.getKeysDigest())) {
            return null;
        }
        return appRevisionService.getKeys();
    }

    public List<AppRevision> fetchRevisions(List<String> keys) {
        return appRevisionService.getMulti(keys);
    }
}
