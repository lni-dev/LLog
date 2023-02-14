/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.llog.base.impl.data.ThrowableLogData;
import me.linusdev.data.so.SOData;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.data.LogSOData;
import de.linusdev.llog.base.impl.data.TextLogData;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface LogInstance extends LogSource {

    /**
     * log given {@link LogData} on given {@link LogLevel}
     * @param logLevel {@link LogLevel}
     * @param data {@link LogData}
     */
    default void log(@NotNull LogLevel logLevel, @NotNull LogData data) {
        getLogger().log(logLevel, this, data);
    }

    /**
     * log given {@code text} on given {@link LogLevel}
     * @param logLevel {@link LogLevel}
     * @param text text to log
     */
    default void log(@NotNull LogLevel logLevel, @NotNull String text) {
        log(logLevel, new TextLogData(text));
    }

    /**
     * log given {@link SOData} as json on given {@link LogLevel}.
     * @param logLevel {@link LogLevel}
     * @param data {@link SOData} to log
     */
    default void log(@NotNull LogLevel logLevel, @NotNull SOData data) {
        log(logLevel, new LogSOData(data));
    }

    /**
     * logs given text with the log level {@link StandardLogLevel#ERROR ERROR}.
     * @param text text to log
     */
    default void logError(@NotNull String text) {
        log(StandardLogLevel.ERROR, text);
    }
    /**
     * logs given text with the log level {@link StandardLogLevel#WARNING WARNING}.
     * @param text text to log
     */
    default void logWarning(@NotNull String text) {
        log(StandardLogLevel.WARNING, text);
    }

    /**
     * logs given text with the log level {@link StandardLogLevel#INFO INFO}.
     * @param text text to log
     */
    default void logInfo(@NotNull String text) {
        log(StandardLogLevel.INFO, text);
    }
    /**
     * logs given text with the log level {@link StandardLogLevel#DEBUG DEBUG}.
     * @param text text to log
     */
    default void logDebug(@NotNull String text) {
        log(StandardLogLevel.DEBUG, text);
    }
    /**
     * logs given text with the log level {@link StandardLogLevel#DEBUG_LOW DEBUG_LOW}.
     * @param text text to log
     */
    default void logDebugLow(@NotNull String text) {
        log(StandardLogLevel.DEBUG_LOW, text);
    }

    default void logThrowable(@NotNull Throwable throwable) {
        log(StandardLogLevel.ERROR, new ThrowableLogData(throwable));
    }

    /**
     * the {@link Logger} this {@link LogInstance} uses.
     * @return {@link Logger}
     */
    @NotNull Logger getLogger();

    /**
     * @see Logger#flush()
     */
    @SuppressWarnings("UnusedReturnValue")
    default boolean flush() {
        return getLogger().flush();
    }

    /**
     * @see Logger#isFlushable()
     */
    default boolean isFlushable() {
        return getLogger().isFlushable();
    }
}
