/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl;

import de.linusdev.llog.base.LogLevel;
import de.linusdev.lutils.color.Color;
import de.linusdev.lutils.color.RGBAColor;
import de.linusdev.lutils.other.UnknownConstantException;
import org.jetbrains.annotations.NotNull;

public enum StandardLogLevel implements LogLevel {

    ERROR("Error", LogLevel.ERROR_NUMERICAL_LOG_LEVEL) {
        @Override
        public @NotNull RGBAColor getLevelNameColor() {
            return Color.RED;
        }

        @Override
        public @NotNull RGBAColor getTextColor() {
            return Color.RED;
        }
    },

    WARNING("Warning", 70) {
        @Override
        public @NotNull RGBAColor getLevelNameColor() {
            return Color.ORANGE;
        }

        @Override
        public @NotNull RGBAColor getTextColor() {
            return Color.ORANGE;
        }
    },

    INFO("Info", 20) {
        @Override
        public @NotNull RGBAColor getLevelNameColor() {
            return Color.CYAN;
        }
    },

    DEBUG("Debug", 0),
    DEBUG_LOW("Debug Low", -5),
    DATA("Data", -10) {
        @Override
        public @NotNull RGBAColor getTextColor() {
            return Color.LIGHT_GRAY;
        }

        @Override
        public @NotNull RGBAColor getLevelNameColor() {
            return Color.LIGHT_GRAY;
        }
    },
    PRIVATE_DATA("Private Data", LogLevel.PRIVATE_DATA_NUMERICAL_LOG_LEVEL) {
        @Override
        public @NotNull RGBAColor getTextColor() {
            return Color.LIGHT_GRAY;
        }

        @Override
        public @NotNull RGBAColor getLevelNameColor() {
            return Color.LIGHT_GRAY;
        }
    },


    ;

    public static @NotNull StandardLogLevel ofName(@NotNull String name) {
        for (StandardLogLevel value : values()) {
            if(value.getName().equals(name))
                return value;
        }

        throw new UnknownConstantException(name);
    }

    private final @NotNull String name;
    private final int level;

    StandardLogLevel(@NotNull String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
