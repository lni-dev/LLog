/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.llog.impl.DefaultPropertyKeys;
import de.linusdev.llog.replacer.LLogStringReplacer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.regex.Pattern;


public class LocalhostLoggerImpl implements Logger {

    @SuppressWarnings("unused")
    public static void adjustReplacer(@NotNull LLogStringReplacer replacer) {

    }

    @SuppressWarnings("unused")
    public static @NotNull Logger create(@NotNull Properties properties) throws IOException {
        String logTo = properties.getProperty(DefaultPropertyKeys.LOG_TO_KEY, "80");
        LogLevel minLogLevel = LogLevel.of(properties.getProperty(DefaultPropertyKeys.MIN_LOG_LEVEL_KEY, "" + StandardLogLevel.DEBUG.getLevel()));

        if(!Pattern.compile("^\\d+$").matcher(logTo).find())
            throw new IllegalArgumentException("Property '" + DefaultPropertyKeys.LOG_TO_KEY + "' must be a valid port number, but was '" + logTo + "'.");

        int port = Integer.parseInt(logTo);

        return new LocalhostLoggerImpl(port, minLogLevel);
    }

    private final @NotNull LLRequestHandler requestHandler;
    private int minimumLogLevel;

    public LocalhostLoggerImpl(int port, @NotNull LogLevel minimumLogLevel) throws IOException {
        setMinimumLogLevel(minimumLogLevel);
        requestHandler = new LLRequestHandler(port, InetAddress.getLoopbackAddress());
        requestHandler.start();
    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {
        if(minimumLogLevel <= logLevel.getLevel()) {

        }
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
        minimumLogLevel = level;
    }

    @Override
    public void shutdown() {
        requestHandler.shutdown();
    }
}
