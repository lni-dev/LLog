/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.nop;

import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

/**
 * No operation {@link Logger}.
 */
public class NOPLogger implements Logger {

    @SuppressWarnings("unused")
    public static @NotNull Logger create(@NotNull Properties properties) {
        return new NOPLogger();
    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {

    }

    @Override
    public boolean flush() {
        return false;
    }

    @Override
    public boolean isFlushable() {
        return false;
    }

    @Override
    public void setMinimumLogLevel(int level) {

    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void shutdown() throws Exception {

    }
}
