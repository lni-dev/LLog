/*
 * Copyright (c) 2023-2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.lutils.color.RGBAColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.regex.Pattern;

public interface LogLevel {

    int ERROR_NUMERICAL_LOG_LEVEL = 100;
    int DEBUG_NUMERICAL_LOG_LEVEL = 0;
    int DEBUG_LOW_NUMERICAL_LOG_LEVEL = 0;
    int PRIVATE_DATA_NUMERICAL_LOG_LEVEL = -100;

    @ApiStatus.Internal
    @NotNull LogLevel _CONFIG = create("Config", DEBUG_NUMERICAL_LOG_LEVEL + 1);
    @ApiStatus.Internal
    @NotNull LogLevel _FINEST = create("Finest", DEBUG_LOW_NUMERICAL_LOG_LEVEL - 45);

    /**
     * Convert {@link Level} to {@link LogLevel}.
     */
    static @NotNull LogLevel of(@NotNull Level level) {
        if(level == Level.OFF)
            return StandardLogLevel.OFF;
        else if(level == Level.WARNING)
            return StandardLogLevel.WARNING;
        else if(level == Level.INFO)
            return StandardLogLevel.INFO;
        else if(level == Level.SEVERE)
            return StandardLogLevel.ERROR;
        else if(level == Level.ALL)
            return StandardLogLevel.ALL;
        else if(level == Level.CONFIG)
            return _CONFIG;
        else if(level == Level.FINE)
            return StandardLogLevel.DEBUG;
        else if(level == Level.FINER)
            return StandardLogLevel.DEBUG_LOW;
        else if(level == Level.FINEST)
            return _FINEST;

        return LogLevel.create(level.getName(), ((level.intValue()/10) - 50));
    }

    /**
     * Create a {@link LogLevel} with given {@code name} and {@code level}
     * @param name {@link #getName()}
     * @param level {@link #getLevel()}
     * @return new {@link LogLevel} as described above
     */
    static @NotNull LogLevel create(@NotNull String name, int level) {
        return new LogLevel() {
            @Override
            public @NotNull String getName() {
                return name;
            }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public String toString() {
                return LogLevel.toString(this);
            }
        };
    }

    /**
     * If given {@code logLevel} is an integer, the returned {@link LogLevel} will have the
     * name "Unknown".
     * @param logLevel int number or any name of {@link StandardLogLevel}.
     * @return given {@code logLevel} as {@link LogLevel}
     */
    static @NotNull LogLevel of(@NotNull String logLevel) {
        if(Pattern.compile("^-?\\d+$").matcher(logLevel).find()) {
            int level = Integer.parseInt(logLevel);
            return create("Unknown", level);
        }

        return StandardLogLevel.ofName(logLevel);
    }

    static @NotNull String toString(@NotNull LogLevel logLevel) {
        return "LogLevel '" + logLevel.getName() + "' (" + logLevel.getLevel() + ")";
    }


    /**
     * the name of this {@link LogLevel}
     * @return name as {@link String}
     */
    @NotNull String getName();

    /**
     * Higher numbers for more important logging information.
     * <br><br>
     * examples:
     * <ul>
     *     <li>
     *         {@link StandardLogLevel#ERROR ERROR} has a numerical log level of {@value #ERROR_NUMERICAL_LOG_LEVEL}
     *     </li>
     *     <li>
     *         {@link StandardLogLevel#PRIVATE_DATA PRIVATE_DATA} has a numerical log level of {@value #PRIVATE_DATA_NUMERICAL_LOG_LEVEL}
     *     </li>
     * </ul>
     *
     * @return the numerical level of this {@link LogLevel}
     * @see Logger#setMinimumLogLevel(int) 
     */
    int getLevel();

    default @Nullable RGBAColor getTextColor() {
        return null;
    }

    default @Nullable RGBAColor getLevelNameColor() {
        return null;
    }

    default @Nullable RGBAColor getSourceColor() {
        return null;
    }

}
