/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.llog.base.impl.StandardLogLevel;
import de.linusdev.lutils.color.RGBAColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LogLevel {

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
