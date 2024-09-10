/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.localhost;

import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @deprecated Not implemented!
 */
@Deprecated()
public class LocalhostLoggerImpl implements Logger {

    private final @NotNull RequestHandler requestHandler;

    public LocalhostLoggerImpl() throws IOException {
        requestHandler = new RequestHandler(80, InetAddress.getLoopbackAddress());
        requestHandler.start();
    }

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

    @Override
    public void shutdown() throws Exception {

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        new LocalhostLoggerImpl();
        Thread.sleep(100000);


    }
}
