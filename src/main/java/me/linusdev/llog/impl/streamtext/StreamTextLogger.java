package me.linusdev.llog.impl.streamtext;

import me.linusdev.llog.base.LogLevel;
import me.linusdev.llog.base.LogSource;
import me.linusdev.llog.base.Logger;
import me.linusdev.llog.base.data.LogData;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StreamTextLogger implements Logger {

    private final @NotNull Writer writer;

    private final @NotNull DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    private int minimumLogLevel;

    public StreamTextLogger(@NotNull OutputStream stream) {
        this(stream, Integer.MIN_VALUE);
    }

    public StreamTextLogger(@NotNull OutputStream stream, int minimumLogLevel) {
        this.writer = new BufferedWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
        this.minimumLogLevel = minimumLogLevel;
    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {
        if(minimumLogLevel < logLevel.getLevel()) {
            if(data.canGenerateString()) {
                log(logLevel.getName(), source.getName(), data.generateString());
            } else {
                log(logLevel.getName(), source.getName(), "unsupported log data.");
            }

        }

    }

    private void log(@NotNull String level, @NotNull String name, @NotNull String text) {
        String timestamp = dateFormat.format(new Date());
        try {
            writer.write("(" + timestamp + " " + level + " " + name + "): " + text);
        } catch (IOException e) {
            System.err.println("logging failed: " + e.getMessage());
        }
    }

    @Override
    public boolean flush() {
        try {
            writer.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean isFlushable() {
        return true;
    }

    @Override
    public void setMinimumLogLevel(int level) {
        this.minimumLogLevel = level;
    }
}
