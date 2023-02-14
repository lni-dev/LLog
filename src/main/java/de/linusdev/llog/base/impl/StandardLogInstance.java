/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl;

import de.linusdev.llog.base.LogInstance;
import de.linusdev.llog.base.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StandardLogInstance implements LogInstance {

    private final @NotNull Logger logger;
    private final @NotNull String name;
    private final @Nullable Map<String, String> information;

    public StandardLogInstance(@NotNull Logger logger, @NotNull String name, @Nullable Map<String, String> information) {
        this.logger = logger;
        this.name = name;
        this.information = information;
    }

    public StandardLogInstance(@NotNull Logger logger, @NotNull Class<?> source) {
        this.logger = logger;
        this.name = source.getSimpleName();
        this.information = new HashMap<>();
        this.information.put("canonical class name", source.getCanonicalName());
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable Map<String, String> getAdditionalInformation() {
        return information;
    }
}
