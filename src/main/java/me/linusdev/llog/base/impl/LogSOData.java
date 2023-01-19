package me.linusdev.llog.base.impl;

import me.linusdev.data.AbstractData;
import me.linusdev.data.Datable;
import me.linusdev.data.so.SOData;
import me.linusdev.llog.base.data.ContentType;
import me.linusdev.llog.base.data.LogData;
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
    public AbstractData<?, ?> getData() {
        return data;
    }
}
