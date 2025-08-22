/*
 * Copyright (c) 2023-2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.Logger;

/**
 * Some keys which may be used between several {@link Logger} or by {@link LLog}.
 */
public class DefaultPropertyKeys {

    public static final String AUTO_FLUSH_KEY = "autoFlush";
    public static final String USE_ANSI_COLORS_KEY = "useAnsiColors";
    public static final String LOGGER_KEY = "logger";
    public static final String NO_INIT_KEY = "noInit";
    public static final String LOG_TO_KEY = "logTo";
    public static final String MIN_LOG_LEVEL_KEY = "minLogLevel";
    public static final String REDIRECT_JAVA_UTIL_LOGGING_TO_LLOG = "redirectJavaUtilLoggingToLLog";

}
