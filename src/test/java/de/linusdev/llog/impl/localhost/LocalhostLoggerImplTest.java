/*
 * Copyright (c) 2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.impl.multi.MultiLoggerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class LocalhostLoggerImplTest {

    private LogInstance log;

    @BeforeEach
    public void beforeEach() {
        Properties properties = new Properties();

        properties.put("logger", MultiLoggerImpl.class.getCanonicalName());
        properties.put("sub-logger-1", "llog/multi/sysout.properties");
        properties.put("sub-logger-2", "llog/localhost/localhost.properties");

        LLog.init(properties);
        log = LLog.getLogInstance("TestSource", null);
    }

    @Test
    void test() throws IOException, InterruptedException {
        int i = 0;
        while (true) {
            log.debug("Some Test log message " + i++);
            Thread.sleep(10000);
        }
    }
}