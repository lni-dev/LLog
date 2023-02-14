/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.impl;

import de.linusdev.llog.LLog;
import de.linusdev.llog.base.Logger;

/**
 * Some keys which may be used between several {@link Logger} or by {@link LLog}.
 */
public class DefaultPropertyKeys {
    public static final String AUTO_FLUSH_KEY = "autoFlush";
    public static final String LOGGER_KEY = "logger";
    public static final String NO_INIT_KEY = "noInit";
    public static final String LOG_TO_KEY = "logTo";
}
