package me.linusdev.llog.base;

import me.linusdev.llog.base.data.LogData;
import org.jetbrains.annotations.NotNull;

public interface LogEntry {


    @NotNull LogLevel getLogLevel();

    @NotNull LogData getLogData();
}
