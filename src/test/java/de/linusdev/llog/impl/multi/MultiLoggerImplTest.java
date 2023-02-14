/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.multi;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.base.impl.StandardLogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class MultiLoggerImplTest {


    private LogInstance log;

    @BeforeEach
    public void beforeEach() {
        Properties properties = new Properties();

        properties.put("logger", MultiLoggerImpl.class.getCanonicalName());
        properties.put("sub-logger-1", "llog/multi/file.properties");
        properties.put("sub-logger-2", "llog/multi/sysout.properties");

        LLog.init(properties);
        log = LLog.getLogInstance("TestSource", null);
    }

    @Test
    public void test() {
        log.log(StandardLogLevel.INFO, "test1");
        log.log(StandardLogLevel.INFO, "test2");
        log.log(StandardLogLevel.INFO, "test3");
        log.logThrowable(new Exception("test :)"));
    }

}