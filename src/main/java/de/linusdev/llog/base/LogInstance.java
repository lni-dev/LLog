package de.linusdev.llog.base;

import me.linusdev.data.so.SOData;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.LogSOData;
import de.linusdev.llog.base.impl.TextLogData;
import org.jetbrains.annotations.NotNull;

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
     * the {@link Logger} this {@link LogInstance} uses.
     * @return {@link Logger}
     */
    @NotNull Logger getLogger();

    /**
     * @see Logger#flush()
     */
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
