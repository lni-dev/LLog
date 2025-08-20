/*
 * Copyright (c) 2023-2025 Linus Andera all rights reserved
 */

package de.linusdev.llog;

import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.base.LogLevel;
import de.linusdev.llog.base.LogSource;
import de.linusdev.llog.base.Logger;
import de.linusdev.llog.base.impl.StandardLogInstance;
import de.linusdev.llog.impl.nop.NOPLogger;
import de.linusdev.llog.replacer.LLogStringReplacer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static de.linusdev.llog.impl.DefaultPropertyKeys.LOGGER_KEY;
import static de.linusdev.llog.impl.DefaultPropertyKeys.NO_INIT_KEY;

/**
 * For documentation about usage see <a href="https://github.com/lni-dev/LLog#LLog">github</a>.
 */
@SuppressWarnings("CallToPrintStackTrace")
public class LLog {

    public static @NotNull Logger logger = new NOPLogger();

    static {
        init();
    }

    public static final @NotNull StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Creates a new {@link LogInstance}, which logs the content of given {@code reader} in a new daemon {@link Thread}.
     * The thread will automatically stop once {@code reader} has no more content ({@code reader.readLine()} returns {@code null}).
     * @param source {@link LogInstance} source
     * @param information {@link LogInstance} information
     * @param reader the reader whose content should be logged.
     * @param logLevel the log level to log to
     */
    @SuppressWarnings("unused")
    public static void createAsyncLogger(
            @NotNull String source,
            @Nullable Map<String, String> information,
            @NotNull BufferedReader reader,
            @NotNull LogLevel logLevel
    ) {
        Thread thread = new Thread(() -> {
            LogInstance log = getLogInstance(source, information);
            try {
                String line;
                while((line = reader.readLine()) != null) {
                    log.log(logLevel, line);
                }
            } catch (Throwable e) {
                log.throwable(e);
            }
        }, "async-logger-" + source.toLowerCase(Locale.ROOT).replace('_', '-'));

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Creates a new {@link LogInstance} for the calling class.
     * <br><br>
     * {@link LogInstance} should only retrieved a single time for a class and saved in a static variable.
     * @return {@link LogInstance} for the calling class
     */
    public static @NotNull LogInstance getLogInstance() {
        Class<?> caller = STACK_WALKER.getCallerClass();
        return new StandardLogInstance(logger, caller);
    }

    /**
     * Creates a new {@link LogInstance} for given source and with given information.
     * @param source the name of the log source. see {@link LogSource#getName()}
     * @param information see {@link LogSource#getAdditionalInformation()}
     * @return a new {@link LogSource} for the given source.
     */
    public static @NotNull LogInstance getLogInstance(@NotNull String source, @Nullable Map<String, String> information) {
        return new StandardLogInstance(logger, source, information);
    }

    public static Logger createLogger(@NotNull String clazz, @NotNull Properties properties, @NotNull LLogStringReplacer replacer) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> loggerClass = Class.forName(clazz);

        if (!Logger.class.isAssignableFrom(loggerClass)) {
            throw new IllegalArgumentException(logger + " class does not implement the Logger interface.");
        }

        try {
            Method adjustMethod = loggerClass.getDeclaredMethod("adjustReplacer", LLogStringReplacer.class);

            if (!Modifier.isStatic(adjustMethod.getModifiers())) {
                System.err.println("adjustReplacer method of " + logger + " is not static.");
            } else {
                adjustMethod.setAccessible(true);
                adjustMethod.invoke(null, replacer);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }

        for (Map.Entry<Object, Object> entry : properties.entrySet())
            entry.setValue(replacer.process((String) entry.getValue()));

        Method createMethod = loggerClass.getDeclaredMethod("create", Properties.class);

        if (!Modifier.isStatic(createMethod.getModifiers()))
            throw new IllegalArgumentException("create method of " + logger + " class is not static.");

        if (!Logger.class.isAssignableFrom(createMethod.getReturnType()))
            throw new IllegalArgumentException("create method of " + logger + " class does not return a Logger.");

        createMethod.setAccessible(true);

        return (Logger) createMethod.invoke(null, properties);
    }

    public static void init(@NotNull Properties properties) {
        try {
            String logger = (String) properties.get(LOGGER_KEY);
            //shutdown the old logger
            LLog.logger.shutdown();
            //create new logger
            LLog.logger = createLogger(logger, properties, new LLogStringReplacer());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot start logger: " + e.getClass().getSimpleName() + ": " + e.getMessage() + ". Defaulting to NOP logger");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.shutdown();
            } catch (Exception e) {
                System.err.println("LLog: Could not shutdown logger: " + e.getMessage());
            }
        }));
    }

    public static void init() {
        try (@Nullable InputStream propertiesFileStream = LLog.class.getClassLoader().getResourceAsStream("llog/llog.properties")) {
            @NotNull final Properties props = new Properties();

            if(propertiesFileStream != null) {
                props.load(propertiesFileStream);

                String noInit = props.getProperty(NO_INIT_KEY, "false");

                if(noInit.equalsIgnoreCase("true")) {
                    System.out.println("LLog: noInit is set... Not initializing any Logger");
                    return;
                }

                init(props);
            } else {
                System.out.println("LLog: No '/llog/llog.properties' file found. Defaulting to NOP logger.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot start logger: " + e.getClass().getSimpleName() + ": " + e.getMessage() + ". Defaulting to NOP logger");
        }
    }

    /**
     * The currently used {@link Logger}.<br><br>
     * You are probably looking for {@link #getLogInstance()}.
     * @return current {@link Logger}
     * @see #getLogInstance()
     */
    public static @NotNull Logger getLogger() {
        return logger;
    }


}
