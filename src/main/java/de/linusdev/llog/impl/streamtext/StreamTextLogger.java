/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl.streamtext;

import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.llog.impl.DefaultPropertyKeys;
import de.linusdev.llog.replacer.LLogStringReplacer;
import de.linusdev.lutils.ansi.sgr.SGR;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamTextLogger implements Logger {

    private final static @NotNull DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private final @NotNull Writer writer;
    private final boolean autoFlush;
    private final boolean useAnsiColors;

    private int minimumLogLevel;

    @SuppressWarnings("unused")
    public static void adjustReplacer(@NotNull LLogStringReplacer replacer) {

    }

    private static final @NotNull Pattern LOG_TO_STREAM_PATTERN = Pattern.compile("^OutputStream:(?<qualifiedClassName>([^/]+\\.)*[^/]+)\\.(?<variable>[^/]+)$");

    @SuppressWarnings("unused")
    public static @NotNull Logger create(@NotNull Properties properties) {
        String logTo = properties.getProperty(DefaultPropertyKeys.LOG_TO_KEY);
        boolean autoFlush = properties.getProperty(DefaultPropertyKeys.AUTO_FLUSH_KEY, "false").equalsIgnoreCase("true");
        boolean useAnsiColors = properties.getProperty(DefaultPropertyKeys.USE_ANSI_COLORS_KEY, "false").equalsIgnoreCase("true");
        LogLevel minLogLevel = LogLevel.of(properties.getProperty(DefaultPropertyKeys.MIN_LOG_LEVEL_KEY, "" + StandardLogLevel.DEBUG.getLevel()));

        Matcher matcher = LOG_TO_STREAM_PATTERN.matcher(logTo);

        if(matcher.find()) {
            String qualifiedClassName = matcher.group("qualifiedClassName");
            String varName = matcher.group("variable");

            try {
                Class<?> clazz = Class.forName(qualifiedClassName);
                Field field = clazz.getField(varName);

                if(!Modifier.isStatic(field.getModifiers()))
                    throw new IllegalArgumentException("Variable '" + qualifiedClassName + "." + varName + "' is not static.");

                if(!OutputStream.class.isAssignableFrom(field.getType()))
                    throw new IllegalArgumentException("Variable '" + qualifiedClassName + "." + varName + "' is no subclass of OutputStream.");


                return new StreamTextLogger((OutputStream) field.get(null), minLogLevel, false, autoFlush, useAnsiColors);

            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("Cannot create OutputStream logger with stream in '" + qualifiedClassName + "." + varName + "'.", e);
            }
        }


        Path path = Paths.get(logTo);
        try {
            Path parent = path.getParent();
            if(!Files.isDirectory(parent))
                Files.createDirectories(parent);

            return new StreamTextLogger(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), minLogLevel, autoFlush, useAnsiColors);
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
    public StreamTextLogger(@NotNull OutputStream stream, @NotNull LogLevel minimumLogLevel, boolean autoFlush, boolean useAnsiColors) {
        this(stream, minimumLogLevel, true, autoFlush, useAnsiColors);
    }

    /**
     *
     * @param stream {@link OutputStream} to write to
     * @param minimumLogLevel The {@link Logger#setMinimumLogLevel(int)}
     * @param wrap {@code true} to wrap the {@link OutputStream} in a {@link BufferedWriter}.
     */
    public StreamTextLogger(@NotNull OutputStream stream, @NotNull LogLevel minimumLogLevel, boolean wrap, boolean autoFlush, boolean useAnsiColors) {
        this.useAnsiColors = useAnsiColors;
        this.autoFlush = autoFlush;
        this.writer = wrap ? new BufferedWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8)) : new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        this.minimumLogLevel = minimumLogLevel.getLevel();
    }

    @Override
    public void log(@NotNull LogLevel logLevel, @NotNull LogSource source, @NotNull LogData data) {
        if(minimumLogLevel <= logLevel.getLevel()) {
            if(data.canGenerateString()) {
                if(useAnsiColors) {
                    String textColorSgr = logLevel.getTextColor() == null ? "" : logLevel.getTextColor().addToSgrAsForeground(new SGR()).construct();
                    log(
                            (logLevel.getLevelNameColor() == null ? "" : logLevel.getLevelNameColor().addToSgrAsForeground(new SGR()).construct()) + logLevel.getName() + SGR.reset(),
                            (logLevel.getSourceColor() == null ? "" : logLevel.getSourceColor().addToSgrAsForeground(new SGR()).construct()) + source.getName()  + SGR.reset(),
                             textColorSgr + data.generateString().replaceAll("\n", "\n"+ textColorSgr) + SGR.reset()
                    );
                } else {
                    log(logLevel.getName(), source.getName(), data.generateString());
                }

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
