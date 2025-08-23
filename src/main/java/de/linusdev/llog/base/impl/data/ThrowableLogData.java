/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl.data;

import de.linusdev.llog.base.data.ContentType;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardContentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableLogData implements LogData {

    private final @Nullable String message;
    private final @NotNull Throwable throwable;

    public ThrowableLogData(@Nullable String message, @NotNull Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    public ThrowableLogData(@NotNull Throwable throwable) {
        this(null, throwable);
    }

    @Override
    public @NotNull ContentType getContentType() {
        return StandardContentType.CUSTOM_OBJECT_THROWABLE;
    }

    @Override
    public boolean canGenerateString() {
        return true;
    }

    @Override
    public @NotNull String generateString() throws UnsupportedOperationException {
        StringWriter string = new StringWriter();
        PrintWriter writer = new PrintWriter(string);

        if(message != null)
            writer.println(message + " Caused by: ");

        throwable.printStackTrace(writer);
        return string.toString();
    }

    @Override
    public boolean hasCustomObject() {
        return true;
    }

    @Override
    public @NotNull Throwable getCustomObject() {
        return throwable;
    }
}
