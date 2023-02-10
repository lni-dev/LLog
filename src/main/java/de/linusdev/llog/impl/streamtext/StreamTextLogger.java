package de.linusdev.llog.impl.streamtext;

import de.linusdev.llog.replacer.LLogStringReplacer;
import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardLogLevel;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class StreamTextLogger implements Logger {

    private final @NotNull DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private final @NotNull Writer writer;
    private final boolean autoFlush;

    private int minimumLogLevel;

    public static void adjustReplacer(@NotNull LLogStringReplacer replacer) {

    }

    public static @NotNull Logger create(@NotNull Properties properties) {
        String logTo = properties.getProperty("logTo");
        boolean autoFlush = properties.getProperty("autoFlush", "false").equalsIgnoreCase("true");

        if(logTo.equals("System.out")) {
            return new StreamTextLogger(System.out, StandardLogLevel.DEBUG.getLevel(), false, autoFlush);
        }

        Path path = Paths.get(logTo);
        System.out.println("Creating " + StreamTextLogger.class.getSimpleName() + ". Logging to '" + path + "'.");
        try {
            Path parent = path.getParent();
            if(!Files.isDirectory(parent))
                Files.createDirectories(parent);

            return new StreamTextLogger(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), autoFlush);
        } catch (IOException e) {
            IllegalArgumentException ex = new IllegalArgumentException("StreamTextLogger: Cannot log to '" + path + "'.");
            //noinspection UnnecessaryInitCause
            ex.initCause(e);
            throw ex;
        }
    }

    /**
     * Creates a new {@link StreamTextLogger} which logs to given stream and with the
     * {@link Logger#setMinimumLogLevel(int) minimum log level} set to {@link StandardLogLevel#DEBUG DEBUG}.
     *
     * @param stream {@link OutputStream} to write to
     */
    public StreamTextLogger(@NotNull OutputStream stream, boolean autoFlush) {
        this(stream, StandardLogLevel.DEBUG.getLevel(), true, autoFlush);
    }

    /**
     *
     * @param stream {@link OutputStream} to write to
     * @param minimumLogLevel The {@link Logger#setMinimumLogLevel(int)}
     * @param wrap {@code true} to wrap the {@link OutputStream} in a {@link BufferedWriter}.
     */
    public StreamTextLogger(@NotNull OutputStream stream, int minimumLogLevel, boolean wrap, boolean autoFlush) {
        this.autoFlush = autoFlush;
        this.writer = wrap ? new BufferedWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8)) : new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        this.minimumLogLevel = minimumLogLevel;
    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {
        if(minimumLogLevel <= logLevel.getLevel()) {
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
            writer.write("(" + timestamp + " " + level + " " + name + "): " + text + System.lineSeparator());
            if(autoFlush) writer.flush();
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

    @Override
    public void shutdown() throws IOException {
        writer.close();
    }
}
