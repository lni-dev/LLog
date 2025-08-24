/*
 * Copyright (c) 2023-2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.multi;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.replacer.LLogStringReplacer;
import de.linusdev.lutils.collections.llist.LLinkedList;
import de.linusdev.lutils.other.debug.DebugInfoStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static de.linusdev.llog.impl.DefaultPropertyKeys.LOGGER_KEY;


@SuppressWarnings("CallToPrintStackTrace")
public class MultiLoggerImpl implements Logger {

    public static final String SUB_LOGGER_KEY_PREFIX = "sub-logger-";

    @SuppressWarnings("unused")
    public static void adjustReplacer(@NotNull LLogStringReplacer replacer) {

    }

    @SuppressWarnings("unused")
    public static @NotNull Logger create(@NotNull Properties properties) {
        MultiLoggerImpl multi = new MultiLoggerImpl();

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (!(entry.getKey() instanceof String))
                continue;
            if (!((String) entry.getKey()).startsWith(SUB_LOGGER_KEY_PREFIX))
                continue;

            String subLoggerPropertiesLocation = (String) entry.getValue();
            try (@Nullable InputStream propertiesFileStream = LLog.class.getClassLoader().getResourceAsStream(subLoggerPropertiesLocation)) {
                @NotNull final Properties props = new Properties();

                if (propertiesFileStream != null)
                    props.load(propertiesFileStream);

                String clazz = (String) props.get(LOGGER_KEY);
                Logger logger = LLog.createLogger(clazz, props, new LLogStringReplacer());
                System.out.println("MultiLogger: started a logger: " + logger.getClass().getSimpleName() + ".");
                multi.addLogger(logger);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("MultiLogger: Cannot start logger with properties '" + subLoggerPropertiesLocation
                        + "': " + e.getClass().getSimpleName() + ": " + e.getMessage() + ".");
            }
        }

        return multi;
    }

    private final @NotNull LLinkedList<Logger> loggers;

    public MultiLoggerImpl() {
        this.loggers = new LLinkedList<>();
    }

    public void addLogger(@NotNull Logger logger) {
        loggers.add(logger);
    }

    public void removeLogger(@NotNull Logger logger) {
        loggers.remove(logger);
    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {
        for (Logger logger : loggers) {
            logger.log(logLevel, source, data);
        }
    }

    @Override
    public boolean flush() {
        boolean ret = false;
        for (Logger logger : loggers) {
            ret = ret || logger.flush();
        }
        return ret;
    }

    @Override
    public boolean isFlushable() {
        boolean ret = false;
        for (Logger logger : loggers) {
            ret = ret || logger.isFlushable();
        }
        return ret;
    }

    @Override
    public void setMinimumLogLevel(int level) {
        for (Logger logger : loggers) {
            logger.setMinimumLogLevel(level);
        }
    }

    @Override
    public void shutdown() throws Exception {
        for (Logger logger : loggers) {
            logger.shutdown();
        }
    }

    @Override
    public @NotNull String info() {
        return new DebugInfoStringBuilder(this, "MultiLogger", 10)
                .addList("loggers", loggers.stream().map(Logger::info).toList())
                .build();
    }
}
