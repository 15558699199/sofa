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
package com.alipay.sofa.rpc.test.invoke;

import com.alipay.remoting.RejectedExecutionPolicy;
import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.exception.SofaTimeOutException;
import com.alipay.sofa.rpc.core.invoke.SofaResponseCallback;
import com.alipay.sofa.rpc.core.request.RequestBase;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.alipay.sofa.rpc.test.ActivelyDestroyTest;
import com.alipay.sofa.rpc.test.HelloService;
import com.alipay.sofa.rpc.test.HelloServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
public class AsyncCallbackTest extends ActivelyDestroyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncCallbackTest.class);

    private ServerConfig serverConfig;
    private ProviderConfig<HelloService> CProvider;
    private ConsumerConfig<HelloService> BConsumer;

    @Test
    public void testAll() {

        serverConfig = new ServerConfig()
                .setPort(22220)
                .setDaemon(false);

        // C服务的服务端
        CProvider = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl(1000))
                .setServer(serverConfig);
        CProvider.export();

        // B调C的客户端
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(50000)
                .setFilterRef(Arrays.asList(filter))
                // .setOnReturn() // 不设置 调用级别设置
                .setDirectUrl("bolt://127.0.0.1:22220");
        HelloService helloService = BConsumer.refer();

        final CountDownLatch latch = new CountDownLatch(1);
        final String[] ret = {null};

        RpcInvokeContext.getContext().setResponseCallback(new SofaResponseCallback() {
            @Override
            public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
                LOGGER.info("B get result: {}", appResponse);
                ret[0] = (String) appResponse;
                latch.countDown();
            }

            @Override
            public void onAppException(Throwable throwable, String methodName, RequestBase request) {
                LOGGER.info("B get app exception: {}", throwable);
                latch.countDown();
            }

            @Override
            public void onSofaException(SofaRpcException sofaException, String methodName,
                                        RequestBase request) {
                LOGGER.info("B get sofa exception: {}", sofaException);
                latch.countDown();
            }
        });

        String ret0 = helloService.sayHello("xxx", 22);
        Assert.assertNull(ret0); // 第一次返回null

        try {
            latch.await(60000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignore) {
        }

        Assert.assertNotNull(ret[0]);
        // 过滤器生效
        assertTrue(ret[0].endsWith("append by async filter"));

        RpcInvokeContext.removeContext();
    }

    @Test
    public void testTimeoutException() {

        serverConfig = new ServerConfig()
                .setPort(22221)
                .setDaemon(false);

        // C服务的服务端
        CProvider = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl(500))
                .setServer(serverConfig);
        CProvider.export();

        // B调C的客户端
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(1)
                .setFilterRef(Arrays.asList(filter))
                // .setOnReturn() // 不设置 调用级别设置
                .setDirectUrl("bolt://127.0.0.1:22221");
        HelloService helloService = BConsumer.refer();

        final CountDownLatch latch = new CountDownLatch(1);
        final String[] ret = {null};

        final boolean[] hasExp = {false};
        RpcInvokeContext.getContext().setResponseCallback(new SofaResponseCallback() {
            @Override
            public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
                LOGGER.info("B get result: {}", appResponse);
                latch.countDown();
            }

            @Override
            public void onAppException(Throwable throwable, String methodName, RequestBase request) {
                LOGGER.info("B get app exception: {}", throwable);
                latch.countDown();
            }

            @Override
            public void onSofaException(SofaRpcException sofaException, String methodName,
                                        RequestBase request) {
                LOGGER.info("B get sofa exception: {}", sofaException);

                if (sofaException instanceof SofaTimeOutException) {
                    hasExp[0] = true;
                }

                latch.countDown();
            }
        });

        String ret0 = helloService.sayHello("xxx", 22);
        Assert.assertNull(ret0); // 第一次返回null

        try {
            latch.await(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignore) {
        }
        // 一定是一个超时异常
        assertTrue(hasExp[0]);

        RpcInvokeContext.removeContext();
    }

    @Test
    public void testNoProviderException() {
        //use bolt, so callback will throw connection closed exception
        serverConfig = new ServerConfig()
                .setPort(22222)
                .setDaemon(false)
                .setProtocol("rest");

        serverConfig.buildIfAbsent().start();

        // B调C的客户端
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(1000)
                .setFilterRef(Arrays.asList(filter))
                // .setOnReturn() // 不设置 调用级别设置
                .setDirectUrl("bolt://127.0.0.1:22222");
        HelloService helloService = BConsumer.refer();

        final CountDownLatch latch = new CountDownLatch(1);
        final String[] ret = {null};

        final boolean[] hasExp = {false};
        RpcInvokeContext.getContext().setResponseCallback(new SofaResponseCallback() {
            @Override
            public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
                LOGGER.info("B get result: {}", appResponse);
                latch.countDown();
            }

            @Override
            public void onAppException(Throwable throwable, String methodName, RequestBase request) {
                LOGGER.info("B get app exception: {}", throwable);
                latch.countDown();
            }

            @Override
            public void onSofaException(SofaRpcException sofaException, String methodName,
                                        RequestBase request) {
                LOGGER.info("B get sofa exception: {}", sofaException);

                if ((sofaException instanceof SofaTimeOutException)) {
                    hasExp[0] = false;
                } else {
                    hasExp[0] = true;
                }

                latch.countDown();
            }
        });

        String ret0 = helloService.sayHello("xxx", 22);
        Assert.assertNull(ret0); // 第一次返回null

        try {
            latch.await(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignore) {
        }
        // 一定是一个超时异常
        assertTrue(hasExp[0]);

        RpcInvokeContext.removeContext();
    }

    @Test
    public void testCallbackCallerHandleException() {

        serverConfig = new ServerConfig()
                .setPort(22223)
                .setDaemon(false);

        // RpcServer for C
        CProvider = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl(0))
                .setServer(serverConfig);
        CProvider.export();

        // RpcClient For B invoke C
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(3000)
                .setFilterRef(Arrays.asList(filter))
                .setRejectedExecutionPolicy(RejectedExecutionPolicy.CALLER_HANDLE_EXCEPTION.name())
                .setDirectUrl("bolt://127.0.0.1:22223");
        HelloService helloService = BConsumer.refer();

        int maxsize = RpcConfigs.getIntValue(RpcOptions.ASYNC_POOL_MAX);
        int queuesize = RpcConfigs.getIntValue(RpcOptions.ASYNC_POOL_QUEUE);
        int invokeCount = (maxsize + queuesize) * 2;
        final CountDownLatch latch = new CountDownLatch(invokeCount);
        AtomicInteger sofaExceptionCount = new AtomicInteger(0);

        SofaResponseCallback callback = new SofaResponseCallback() {
            @Override
            public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
                LOGGER.info("B get result: {}", appResponse);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                latch.countDown();
            }

            @Override
            public void onAppException(Throwable throwable, String methodName, RequestBase request) {
                LOGGER.info("B get app exception: {}", throwable);
                latch.countDown();
            }

            @Override
            public void onSofaException(SofaRpcException sofaException, String methodName,
                                        RequestBase request) {
                LOGGER.info("B get sofa exception: {}", sofaException);
                latch.countDown();
                sofaExceptionCount.addAndGet(1);
            }
        };

        for (int i = 0; i < invokeCount; i++) {
            RpcInvokeContext.getContext().setResponseCallback(callback);
            helloService.sayHello("" + i, 33);
        }

        try {
            latch.await(100L * invokeCount, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignore) {
        }

        // Exception callbacks are triggered by IO threads after the thread pool is full, so the total number of responses received is equal to the total number of calls
        assertEquals(0, latch.getCount());
        // The number of exception callbacks triggered by IO threads must exist 
        assertTrue(sofaExceptionCount.get() > 0);
        RpcInvokeContext.removeContext();
    }

    @Test
    public void testCallbackCallerRuns() {

        serverConfig = new ServerConfig()
                .setPort(22224)
                .setDaemon(false);

        // RpcServer for C
        CProvider = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl(0))
                .setServer(serverConfig);
        CProvider.export();

        // RpcClient For B invoke C
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(3000)
                .setFilterRef(Arrays.asList(filter))
                .setRejectedExecutionPolicy(RejectedExecutionPolicy.CALLER_RUNS.name())
                .setDirectUrl("bolt://127.0.0.1:22224");
        HelloService helloService = BConsumer.refer();

        int maxsize = RpcConfigs.getIntValue(RpcOptions.ASYNC_POOL_MAX);
        int queuesize = RpcConfigs.getIntValue(RpcOptions.ASYNC_POOL_QUEUE);
        int invokeCount = (maxsize + queuesize) * 2;
        final CountDownLatch latch = new CountDownLatch(invokeCount);
        AtomicInteger runCount = new AtomicInteger(0);

        SofaResponseCallback callback = new SofaResponseCallback() {
            @Override
            public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
                LOGGER.info("B get result: {}", appResponse);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                runCount.addAndGet(1);
                latch.countDown();
            }

            @Override
            public void onAppException(Throwable throwable, String methodName, RequestBase request) {
                LOGGER.info("B get app exception: {}", throwable);
                latch.countDown();
            }

            @Override
            public void onSofaException(SofaRpcException sofaException, String methodName,
                                        RequestBase request) {
                LOGGER.info("B get sofa exception: {}", sofaException);
                latch.countDown();
            }
        };

        for (int i = 0; i < invokeCount; i++) {
            RpcInvokeContext.getContext().setResponseCallback(callback);
            helloService.sayHello("" + i, 33);
        }

        try {
            latch.await(100L * invokeCount, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignore) {
        }

        assertEquals(0, latch.getCount());
        // Invoke callbacks are triggered by IO threads after the thread pool is full, so the total number of normal responses received is equal to the total number of calls
        assertEquals(invokeCount, runCount.get());
        RpcInvokeContext.removeContext();
    }

    @Test
    public void testCallbackDiscard() {

        serverConfig = new ServerConfig()
                .setPort(22225)
                .setDaemon(false);

        // RpcServer For C
        CProvider = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl(0))
                .setServer(serverConfig);
        CProvider.export();

        // RpcClient For B invoke C
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(3000)
                .setFilterRef(Arrays.asList(filter))
                .setRejectedExecutionPolicy(RejectedExecutionPolicy.DISCARD.name())
                .setDirectUrl("bolt://127.0.0.1:22225");
        HelloService helloService = BConsumer.refer();

        int maxsize = RpcConfigs.getIntValue(RpcOptions.ASYNC_POOL_MAX);
        int queuesize = RpcConfigs.getIntValue(RpcOptions.ASYNC_POOL_QUEUE);
        int invokeCount = (maxsize + queuesize) * 2;
        final CountDownLatch latch = new CountDownLatch(invokeCount);

        SofaResponseCallback callback = new SofaResponseCallback() {
            @Override
            public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
                LOGGER.info("B get result: {}", appResponse);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                latch.countDown();
            }

            @Override
            public void onAppException(Throwable throwable, String methodName, RequestBase request) {
                LOGGER.info("B get app exception: {}", throwable);
                latch.countDown();
            }

            @Override
            public void onSofaException(SofaRpcException sofaException, String methodName,
                                        RequestBase request) {
                LOGGER.info("B get sofa exception: {}", sofaException);
                latch.countDown();
            }
        };

        for (int i = 0; i < invokeCount; i++) {
            RpcInvokeContext.getContext().setResponseCallback(callback);
            helloService.sayHello("" + i, 33);
        }

        try {
            latch.await(100L * invokeCount, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignore) {
        }

        LOGGER.info("Discard response callback: " + (invokeCount - latch.getCount()));
        // The total number of callbacks received in discard mode must be less than the total number of requests
        assertTrue(latch.getCount() > 0);
        RpcInvokeContext.removeContext();
    }

    @Test(expected = SofaRpcException.class)
    public void testRejectedExecutionPolicyIllegal() {

        serverConfig = new ServerConfig()
                .setPort(22226)
                .setDaemon(false);

        // RpcServer For C
        CProvider = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl(0))
                .setServer(serverConfig);
        CProvider.export();

        // RpcClient For B invoke C
        Filter filter = new TestAsyncFilter();
        BConsumer = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName())
                .setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK)
                .setTimeout(3000)
                .setFilterRef(Arrays.asList(filter))
                .setRejectedExecutionPolicy("WRONG_POLICY")
                .setDirectUrl("bolt://127.0.0.1:22226");
        HelloService helloService = BConsumer.refer();

        // Calling when setting the wrong rejected execution policy will throw an exception
        helloService.sayHello("test", 33);
    }

    @After
    public void after() {
        if (CProvider != null) {
            CProvider.unExport();
        }
        if (BConsumer != null) {
            BConsumer.unRefer();
        }
        if (serverConfig != null) {
            serverConfig.destroy();
        }
    }
}
