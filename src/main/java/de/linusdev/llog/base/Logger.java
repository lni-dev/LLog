/*
 * Copyright (c) 2023-2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.data.so.SOData;
import de.linusdev.llog.LLog;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.data.LogSOData;
import de.linusdev.llog.base.impl.data.TextLogData;
import de.linusdev.llog.impl.streamtext.StreamTextLogger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Logger specification
 * <br><br>
 * This interface must be implemented by all custom {@link Logger} implementation. An Example implementation
 * is the {@link StreamTextLogger}.
 * <br><br>
 * Every {@link Logger} implementation must implement a {@code create} method. This method must be {@code static}.
 * The exact syntax of the method must be:<br>
 * <pre>{@code public static Logger create(Properties properties)}</pre>
 * The {@code properties} parameter will never be {@code null} and the returned {@link Logger} must not be {@code null}.
 * <br><br>
 * Optionally every {@link Logger} implementation may implement a static {@code adjustReplacer} method.
 * The exact syntax of this method must be:<br>
 * <pre>{@code public static void adjustReplacer(LLogStringReplacer replacer)}</pre>
 * The {@code replacer} parameter will never be {@code null}.
 *
 */
public interface Logger {

    /**
     * log given {@link LogData} on given {@link LogLevel}
     * @param logLevel {@link LogLevel}
     * @param data {@link LogData}
     */
    void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data);

    /**
     * log given {@code text} on given {@link LogLevel}
     * @param logLevel {@link LogLevel}
     * @param text text to log
     */
    default void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull String text) {
        log(logLevel, source, new TextLogData(text));
    }

    /**
     * log given {@link SOData} as json on given {@link LogLevel}.
     * @param logLevel {@link LogLevel}
     * @param data {@link SOData} to log
     */
    default void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull SOData data) {
        log(logLevel, source, new LogSOData(data));
    }

    /**
     * Flushes the logger if possible.
     * @return whether the flush was successful.
     */
    boolean flush();

    /**
     * Whether this stream is flushable.
     * @return {@code false} if {@link #flush()} will always return {@code false}.
     */
    boolean isFlushable();

    /**
     * Sets the minimum numerical log level to {@link LogLevel#getLevel()}.
     * @param level minimum {@link LogLevel}
     */
    default void setMinimumLogLevel(@NotNull LogLevel level) {
        setMinimumLogLevel(level.getLevel());
    }

    /**
     * Sets the minimum numerical log level to given {@code level}.
     * @param level minimum numerical log level
     */
    void setMinimumLogLevel(int level);

    /**
     * Shuts down the {@link Logger}. This method should be used to release resource or to close streams.
     * This will be automatically called by {@link LLog LLog}.
     */
    @ApiStatus.Internal
    void shutdown() throws Exception;

    /**
     * A string containing information about this logger (e.g. log destination, min log level, ...)
     */
    @NotNull String info();

}
