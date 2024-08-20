/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.lutils.color.RGBAColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public interface LogLevel {

    /**
     * If given {@code logLevel} is an integer, the returned {@link LogLevel} will have the
     * name "Unknown".
     * @param logLevel int number or any name of {@link StandardLogLevel}.
     * @return given {@code logLevel} as {@link LogLevel}
     */
    static @NotNull LogLevel of(@NotNull String logLevel) {
        if(Pattern.compile("^\\d+$").matcher(logLevel).find()) {
            int level = Integer.parseInt(logLevel);
            return new LogLevel() {
                @Override
                public @NotNull String getName() {
                    return "Unknown";
                }

                @Override
                public int getLevel() {
                    return level;
                }
            };
        }

        return StandardLogLevel.ofName(logLevel);
    }

    int ERROR_NUMERICAL_LOG_LEVEL = 100;
    int PRIVATE_DATA_NUMERICAL_LOG_LEVEL = -100;

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
