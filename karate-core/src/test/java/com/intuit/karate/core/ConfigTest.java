package com.intuit.karate.core;

import com.intuit.karate.LogAppender;
import com.intuit.karate.Logger;
import com.intuit.karate.core.Config;
import com.intuit.karate.core.DummyClient;
import com.intuit.karate.core.MockHandler;
import com.intuit.karate.core.Variable;
import com.intuit.karate.shell.StringLogAppender;
import com.oracle.truffle.api.instrumentation.GenerateWrapper.Ignore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.intuit.karate.TestUtils.FeatureBuilder;
import static com.intuit.karate.TestUtils.match;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

class ConfigTest {
    Config config;

    @BeforeEach
    void beforeEach() {
        config = new Config();
    }

    @Test
    void configSetsUrlCorrectly() {        
        config.configure("url", new Variable("www.testing.com"));
        assertEquals("www.testing.com", config.getUrl());
    }

    @Test
    void configSetsRespinseDelayToZeroIfValueIsNull() {
        config.configure("responseDelay", new Variable(null));
        assertEquals(0, config.getResponseDelay());
    }

    @Test
    void configResovesReportCorrectly() {
        Map<String, Boolean> map = new HashMap<>();
        boolean showAllSteps = true;
        boolean ShowLog = true;
        map.put("showAllSteps", true);
        map.put("showLog", true);
        config.configure("report", new Variable(map));
        assertEquals(showAllSteps, config.isShowAllSteps());
        assertEquals(ShowLog, config.isShowLog());
    }
}


