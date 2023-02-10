package de.linusdev.llog.replacer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class LocationObject implements ReplaceObject {

    private final @Nullable Path location;

    public LocationObject(@Nullable Path location) {
        this.location = location;
    }

    @Override
    public @Nullable ReplaceObject getObject(@NotNull String name) {
        if(location == null)
            return null;

        if(name.equalsIgnoreCase("parent"))
            return new LocationObject(location.getParent());

        return null;
    }

    @Override
    public @Nullable String getProperty(@NotNull String name) {
        return null;
    }

    @Override
    public String toString() {
        return location == null ? null : location.toString();
    }
}
