/*
 * Copyright (c) 2025 Linus Andera all rights reserved
 */

package de.linusdev.llog;

import de.linusdev.lutils.other.log.Logger;
import de.linusdev.lutils.other.log.LoggerProviderService;
import org.jetbrains.annotations.NotNull;

public class LutilsLogProvider implements LoggerProviderService {
    @Override
    public @NotNull Logger getLogger(@NotNull Class<?> source) {
        return LLog.getLogInstance(source.getSimpleName(), null);
    }

    @Override
    public @NotNull Logger getLogger(@NotNull String source) {
        return LLog.getLogInstance(source, null);
    }
}
