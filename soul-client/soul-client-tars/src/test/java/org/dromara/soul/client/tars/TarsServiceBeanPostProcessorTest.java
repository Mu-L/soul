/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.dromara.soul.client.tars;

import org.dromara.soul.client.tars.common.annotation.SoulTarsClient;
import org.dromara.soul.client.tars.common.annotation.SoulTarsService;
import org.dromara.soul.register.common.config.SoulRegisterCenterConfig;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Properties;

/**
 * Test case for {@link TarsServiceBeanPostProcessor}.
 *
 * @author tydhot
 */
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TarsServiceBeanPostProcessorTest {
    private static TarsServiceBeanPostProcessor tarsServiceBeanPostProcessor;

    @BeforeClass
    public static void init() {
        Properties properties = new Properties();
        properties.setProperty("contextPath", "/tars");
        properties.setProperty("ipAndPort", "localhost:8080");
        properties.setProperty("port", "21715");

        SoulRegisterCenterConfig mockRegisterCenter = new SoulRegisterCenterConfig();
        mockRegisterCenter.setServerLists("http://localhost:58080");
        mockRegisterCenter.setRegisterType("http");
        mockRegisterCenter.setProps(properties);
        tarsServiceBeanPostProcessor = new TarsServiceBeanPostProcessor(mockRegisterCenter);
    }

    @Test
    public void testPostProcessAfterInitialization() {
        TarsDemoService serviceFactoryBean = new TarsDemoService();
        tarsServiceBeanPostProcessor
                .postProcessAfterInitialization(serviceFactoryBean, "SoulTarsTest");
    }

    @Test
    public void testPostProcessNormalBean() {
        tarsServiceBeanPostProcessor
                .postProcessAfterInitialization(new Object(), "normalBean");
    }

    @SoulTarsService(serviceName = "testObj")
    static class TarsDemoService {
        @SoulTarsClient(path = "hello")
        public String test(final String hello) {
            return hello + "";
        }
    }
}
