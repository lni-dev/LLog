package me.linusdev.llog.base.impl;

import me.linusdev.llog.base.LogLevel;
import org.jetbrains.annotations.NotNull;

public enum StandardLogLevel implements LogLevel {

    ERROR("Error", LogLevel.ERROR_NUMERICAL_LOG_LEVEL),
    WARNING("Warning", 70),
    INFO("Info", 20),
    DEBUG("Debug", 0),
    DEBUG_LOW("Debug Low", -5),
    DATA("Data", -10),
    PRIVATE_DATA("Private Data", LogLevel.PRIVATE_DATA_NUMERICAL_LOG_LEVEL),


    ;


    private final @NotNull String name;
    private final int level;

    StandardLogLevel(@NotNull String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
