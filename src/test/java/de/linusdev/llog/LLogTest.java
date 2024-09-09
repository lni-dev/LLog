/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog;

import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.base.impl.StandardLogLevel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LLogTest {

    @Test
    public void testInit() throws IOException {
        Properties properties = new Properties();

        properties.put("logger", "de.linusdev.llog.impl.streamtext.StreamTextLogger");
        properties.put("logTo", "testOutput/log-{datetime}.log");

        LLog.init(properties);
        LogInstance log = LLog.getLogInstance("TestSource", null);
        log.log(StandardLogLevel.INFO, "Test");
        log.flush();

        Path logFile = Paths.get(properties.getProperty("logTo"));
        String content = Files.readString(logFile).replace(System.lineSeparator(), "");
        Matcher matcher = Pattern.compile("\\(\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d \\d\\d:\\d\\d Info TestSource\\): Test").matcher(content);

        assertTrue(matcher.find());
        assertEquals(0, matcher.start());
        assertEquals(matcher.end(), content.length());

        Files.delete(logFile);
    }

}