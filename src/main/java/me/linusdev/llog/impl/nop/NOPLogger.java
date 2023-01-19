package me.linusdev.llog.impl.nop;

import me.linusdev.llog.base.LogLevel;
import me.linusdev.llog.base.LogSource;
import me.linusdev.llog.base.Logger;
import me.linusdev.llog.base.data.LogData;
import org.jetbrains.annotations.NotNull;

/**
 * No operation {@link Logger}.
 */
public class NOPLogger implements Logger {
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
}
