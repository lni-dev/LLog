/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.data.so.SOData;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.llog.base.impl.data.LogSOData;
import de.linusdev.llog.base.impl.data.TextLogData;
import de.linusdev.llog.base.impl.data.TextSupplierLogData;
import de.linusdev.llog.base.impl.data.ThrowableLogData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

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
     * log text supplied by given {@code textSupplier} on given {@link LogLevel}.
     * {@code textSupplier} will only be called, if this log message is actually logged.
     * For example, it may not be logged, if the {@link Logger#setMinimumLogLevel(int) minimum log level} is higher than
     * given {@code logLevel}.
     * @param logLevel {@link LogLevel}
     * @param textSupplier supplier to supply text to log
     */
    default void log(@NotNull LogLevel logLevel, @NotNull Supplier<@NotNull String> textSupplier) {
        log(logLevel, new TextSupplierLogData(textSupplier));
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
     * Same as {@link #log(LogLevel, String)} with log level {@link StandardLogLevel#ERROR ERROR}.
     */
    default void error(@NotNull String text) {
        log(StandardLogLevel.ERROR, text);
    }

    /**
     * Same as {@link #log(LogLevel, Supplier)} with log level {@link StandardLogLevel#ERROR ERROR}.
     */
    default void error(@NotNull Supplier<@NotNull String> text) {
        log(StandardLogLevel.ERROR, text);
    }

    /**
     * Same as {@link #log(LogLevel, String)} with log level {@link StandardLogLevel#WARNING WARNING}.
     */
    default void warning(@NotNull String text) {
        log(StandardLogLevel.WARNING, text);
    }

    /**
     * Same as {@link #log(LogLevel, Supplier)} with log level {@link StandardLogLevel#WARNING WARNING}.
     */
    default void warning(@NotNull Supplier<@NotNull String> text) {
        log(StandardLogLevel.WARNING, text);
    }

    /**
     * Same as {@link #log(LogLevel, String)} with log level {@link StandardLogLevel#INFO INFO}.
     */
    default void info(@NotNull String text) {
        log(StandardLogLevel.INFO, text);
    }

    /**
     * Same as {@link #log(LogLevel, Supplier)} with log level {@link StandardLogLevel#INFO INFO}.
     */
    default void info(@NotNull Supplier<@NotNull String> text) {
        log(StandardLogLevel.INFO, text);
    }

    /**
     * Same as {@link #log(LogLevel, String)} with log level {@link StandardLogLevel#DEBUG DEBUG}.
     */
    default void debug(@NotNull String text) {
        log(StandardLogLevel.DEBUG, text);
    }

    /**
     * Same as {@link #log(LogLevel, Supplier)} with log level {@link StandardLogLevel#DEBUG DEBUG}.
     */
    default void debug(@NotNull Supplier<@NotNull String> text) {
        log(StandardLogLevel.DEBUG, text);
    }

    /**
     * Same as {@link #log(LogLevel, String)} with log level {@link StandardLogLevel#DEBUG_LOW DEBUG_LOW}.
     */
    default void debugLow(@NotNull String text) {
        log(StandardLogLevel.DEBUG_LOW, text);
    }

    /**
     * Same as {@link #log(LogLevel, Supplier)} with log level {@link StandardLogLevel#DEBUG_LOW DEBUG_LOW}.
     */
    default void debugLow(@NotNull Supplier<@NotNull String> text) {
        log(StandardLogLevel.DEBUG_LOW, text);
    }

    /**
     * Logs a {@link ThrowableLogData} with given {@code throwable} and log level {@link StandardLogLevel#ERROR ERROR}.
     */
    default void throwable(@NotNull Throwable throwable) {
        log(StandardLogLevel.ERROR, new ThrowableLogData(throwable));
    }

    /**
     * Logs a {@link ThrowableLogData} with given {@code message} and {@code throwable} and log level {@link StandardLogLevel#ERROR ERROR}.
     */
    default void throwable(@Nullable String message, @NotNull Throwable throwable) {
        log(StandardLogLevel.ERROR, new ThrowableLogData(message, throwable));
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

    /*
     * Old Logging functions
     */

    /**
     * logs given text with the log level {@link StandardLogLevel#ERROR ERROR}.
     * @param text text to log
     */
    @Deprecated(since = "replaced with error()")
    default void logError(@NotNull String text) {
        log(StandardLogLevel.ERROR, text);
    }
    /**
     * logs given text with the log level {@link StandardLogLevel#WARNING WARNING}.
     * @param text text to log
     */
    @Deprecated(since = "replaced with warning()")
    default void logWarning(@NotNull String text) {
        log(StandardLogLevel.WARNING, text);
    }

    /**
     * logs given text with the log level {@link StandardLogLevel#INFO INFO}.
     * @param text text to log
     */
    @Deprecated(since = "replaced with info()")
    default void logInfo(@NotNull String text) {
        log(StandardLogLevel.INFO, text);
    }
    /**
     * logs given text with the log level {@link StandardLogLevel#DEBUG DEBUG}.
     * @param text text to log
     */
    @Deprecated(since = "replaced with debug()")
    default void logDebug(@NotNull String text) {
        log(StandardLogLevel.DEBUG, text);
    }
    /**
     * logs given text with the log level {@link StandardLogLevel#DEBUG_LOW DEBUG_LOW}.
     * @param text text to log
     */
    @Deprecated(since = "replaced with debugLow()")
    default void logDebugLow(@NotNull String text) {
        log(StandardLogLevel.DEBUG_LOW, text);
    }

    @Deprecated(since = "replaced with throwable()")
    default void logThrowable(@NotNull Throwable throwable) {
        log(StandardLogLevel.ERROR, new ThrowableLogData(throwable));
    }
}
