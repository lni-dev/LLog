/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.replacer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class MutableReplaceObject implements ReplaceObject {

    private final @NotNull HashMap<String, ReplaceObject> objects;
    private final @NotNull HashMap<String, String> properties;

    public MutableReplaceObject() {
        objects = new HashMap<>();
        properties = new HashMap<>();
    }


    @SuppressWarnings("UnusedReturnValue")
    public @NotNull MutableReplaceObject addObject(@NotNull String name, @NotNull ReplaceObject object) {
        objects.put(name, object);
        return this;
    }


    public @NotNull MutableReplaceObject addProperty(@NotNull String name, @NotNull String property) {
        properties.put(name, property);
        return this;
    }

    @Override
    public @Nullable ReplaceObject getObject(@NotNull String name) {
        return objects.get(name);
    }

    @Override
    public @Nullable String getProperty(@NotNull String name) {
        return properties.get(name);
    }

    @Override
    public String toString() {
        return "obj";
    }
}
