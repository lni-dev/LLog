/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.streamtext;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.base.impl.StandardLogLevel;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamTextLoggerTest {

    public static final @NotNull ByteArrayOutputStream stream = new ByteArrayOutputStream();

    private LogInstance log;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("dontLogTime", "true");
        Properties properties = new Properties();

        properties.put("logger", "de.linusdev.llog.impl.streamtext.StreamTextLogger");
        properties.put("logTo", "OutputStream:de.linusdev.llog.impl.streamtext.StreamTextLoggerTest.stream");
        properties.put("autoFlush", "true");

        LLog.init(properties);
        log = LLog.getLogInstance("TestSource", null);
    }

    @Test
    public void test() {
        log.log(StandardLogLevel.INFO, "test");
        log.log(StandardLogLevel.INFO, "test");
        log.log(StandardLogLevel.INFO, "test");

        String result = stream.toString();
        Matcher matcher = Pattern.compile("^\\(\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d \\d\\d:\\d\\d ", Pattern.MULTILINE).matcher(result);
        StringBuilder sb = new StringBuilder();
        while (matcher.find())
            matcher.appendReplacement(sb, "( ");
        matcher.appendTail(sb);

        assertEquals(
                """
                        ( Info TestSource): test\r
                        ( Info TestSource): test\r
                        ( Info TestSource): test\r
                        """,
                sb.toString()
        );


    }

}