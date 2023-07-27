/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Not implemented!
 */
@Deprecated()
public class LocalhostLoggerImpl implements Logger {

    public LocalhostLoggerImpl() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
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

    @Override
    public void shutdown() throws Exception {

    }
}
