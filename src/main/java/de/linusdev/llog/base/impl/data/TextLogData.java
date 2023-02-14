/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl.data;

import de.linusdev.llog.base.data.ContentType;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardContentType;
import org.jetbrains.annotations.NotNull;

public class TextLogData implements LogData {

    private final @NotNull String text;

    public TextLogData(@NotNull String text) {
        this.text = text;
    }

    @Override
    public @NotNull ContentType getContentType() {
        return StandardContentType.TEXT_PLAIN_UTF_8;
    }

    @Override
    public boolean canGenerateString() {
        return true;
    }

    @Override
    public @NotNull String generateString() throws UnsupportedOperationException {
        return text;
    }

    @Override
    public boolean canGenerateBytes() {
        return false;
    }

    @Override
    public byte @NotNull [] generateBytes() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasCustomObject() {
        return false;
    }

    @Override
    public @NotNull Object getCustomObject() {
        throw new UnsupportedOperationException();
    }
}
