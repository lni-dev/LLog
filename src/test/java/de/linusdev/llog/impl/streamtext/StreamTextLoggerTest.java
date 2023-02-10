package de.linusdev.llog.impl.streamtext;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.base.impl.StandardLogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class StreamTextLoggerTest {

    private LogInstance log;

    @BeforeEach
    public void beforeEach() {
        Properties properties = new Properties();

        properties.put("logger", "de.linusdev.llog.impl.streamtext.StreamTextLogger");
        properties.put("logTo", "System.out");
        properties.put("autoFlush", "true");

        LLog.init(properties);
        log = LLog.getLogInstance("TestSource", null);
    }

    @Test
    public void test() {
        log.log(StandardLogLevel.INFO, "test");
        log.log(StandardLogLevel.INFO, "test");
        log.log(StandardLogLevel.INFO, "test");
    }

}