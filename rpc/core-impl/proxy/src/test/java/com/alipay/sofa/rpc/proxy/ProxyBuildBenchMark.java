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
package com.alipay.sofa.rpc.proxy;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaowang
 * @version : ProxyBenchMark.java, v 0.1 2022年08月10日 4:53 PM zhaowang
 */

@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ProxyBuildBenchMark {

    public static final String JAVASSIST = "javassist";
    public static final String JDK = "jdk";
    public static final String BYTEBUDDY = "bytebuddy";

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(ProxyBuildBenchMark.class.getSimpleName()).build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() throws Exception {
        System.setProperty("sofa.rpc.proxy.disableCache", "true");
    }

    @Benchmark
    public void javassistBuild(Blackhole bh) throws Exception {
        TestInterface testInterface = ProxyFactory.buildProxy(JAVASSIST, TestInterface.class, null);
        bh.consume(testInterface);
    }

    @Benchmark
    public void jdkBuild(Blackhole bh) throws Exception {
        TestInterface testInterface = ProxyFactory.buildProxy(JDK, TestInterface.class, null);
        bh.consume(testInterface);
    }

    @Benchmark
    public void bytebuddyBuild(Blackhole bh) throws Exception {
        TestInterface testInterface = ProxyFactory.buildProxy(BYTEBUDDY, TestInterface.class, null);
        bh.consume(testInterface);
    }

}