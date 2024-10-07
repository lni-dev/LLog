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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.regex.Pattern;


public class LocalhostLoggerImpl implements Logger {

    public static final @NotNull String WEBSITE_PREFIX = "llog";

    @SuppressWarnings("unused")
    public static void adjustReplacer(@NotNull LLogStringReplacer replacer) {

    }

    @SuppressWarnings("unused")
    public static @NotNull Logger create(@NotNull Properties properties) throws IOException, NoSuchAlgorithmException {
        String sitePort = properties.getProperty(DefaultPropertyKeys.LOG_TO_KEY);
        LogLevel minLogLevel = LogLevel.of(properties.getProperty(DefaultPropertyKeys.MIN_LOG_LEVEL_KEY, "" + StandardLogLevel.DEBUG.getLevel()));

        if(sitePort != null && !Pattern.compile("^\\d+$").matcher(sitePort).find())
            throw new IllegalArgumentException("Property '" + DefaultPropertyKeys.LOG_TO_KEY + "' must be a valid port number, but was '" + sitePort + "'.");

        return new LocalhostLoggerImpl(
                sitePort == null ? -1 : Integer.parseInt(sitePort),
                minLogLevel
        );
    }

    private final @Nullable LLRequestHandler requestHandler;
    private final @NotNull LLWebsocket websocket;
    private int minimumLogLevel;

    public LocalhostLoggerImpl(int sitePort, @NotNull LogLevel minimumLogLevel) throws IOException, NoSuchAlgorithmException {
        setMinimumLogLevel(minimumLogLevel);

        websocket = new LLWebsocket();
        websocket.start();

        if(sitePort != -1) {
            requestHandler = new LLRequestHandler(sitePort, InetAddress.getLoopbackAddress(), websocket);
            requestHandler.start();
        }else {
            requestHandler = null;
        }

    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {
        if(minimumLogLevel <= logLevel.getLevel()) {
            if(data.canGenerateString()) {
                websocket.sendToAll(data.generateString());
            }
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
        if(requestHandler != null)
            requestHandler.shutdown();
        websocket.shutdown();
    }
}
