/*
 * Copyright (c) 2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl;

import de.linusdev.llog.base.LogSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public class SimpleLogSource implements LogSource {

    private final @NotNull String name;

    public SimpleLogSource(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable Map<String, String> getAdditionalInformation() {
        return null;
    }
}
