package me.linusdev.llog.arch.data;

import org.jetbrains.annotations.NotNull;

public interface LogData {

    /**
     * The {@link ContentType} of this {@link LogData}.
     * @return {@link ContentType}
     */
    @NotNull ContentType getContentType();

}
