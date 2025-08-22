/*
 * Copyright (c) 2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.java;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LLogHandler extends Handler {

    public LLogHandler() {
        setLevel(Level.ALL);

    }

    @Override
    public void publish(LogRecord record) {
        LLog.getLogger().log(
                LogLevel.of(record.getLevel()),
                LogSource.of(record.getLoggerName()),
                record.getMessage()
        );
    }

    @Override
    public void flush() {
        LLog.getLogger().flush();
    }

    @Override
    public void close() throws SecurityException {
        // Do nothing
    }
}
