/*
 * Copyright (c) 2023-2025 Linus Andera all rights reserved
 */

package de.linusdev.llog.base;

import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.SimpleLogSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * The source of a {@link LogData}.
 */
public interface LogSource {

    static @NotNull LogSource of(@NotNull String name) {
        return new SimpleLogSource(name);
    }

    /**
     * Name of this {@link LogSource}.
     * @return name as {@link String}
     */
    @NotNull String getName();

    /**
     * The returned map should contain additional information about this {@link LogSource}.
     * This information may be omitted by some logging implementations.<br><br>
     * The key should be a describing name of the information given as value.
     * @return {@link Map} containing additional information
     */
    @Nullable Map<String, String> getAdditionalInformation();

}
