/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl.data;

import de.linusdev.llog.base.data.ContentType;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardContentType;
import de.linusdev.data.AbstractData;
import de.linusdev.data.Datable;
import de.linusdev.data.so.SOData;
import org.jetbrains.annotations.NotNull;

public class LogSOData implements LogData, Datable {

    private final @NotNull SOData data;

    public LogSOData(@NotNull SOData data) {
        this.data = data;
    }

    @Override
    public @NotNull ContentType getContentType() {
        return StandardContentType.APPLICATION_JSON;
    }

    @Override
    public boolean canGenerateString() {
        return true;
    }

    @Override
    public @NotNull String generateString() throws UnsupportedOperationException {
        return data.toJsonString().toString();
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
        return true;
    }

    @Override
    public @NotNull SOData getCustomObject() {
        return data;
    }

    @Override
    public AbstractData<?, ?> getData() {
        return data;
    }
}
