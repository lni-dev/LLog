/*
 * Copyright (c) 2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl.data;

import de.linusdev.llog.base.data.ContentType;
import de.linusdev.llog.base.data.LogData;
import de.linusdev.llog.base.impl.StandardContentType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TextSupplierLogData implements LogData {

    private final @NotNull Supplier<@NotNull String> supplier;

    public TextSupplierLogData(@NotNull Supplier<@NotNull String> supplier) {
        this.supplier = supplier;
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
        return supplier.get();
    }
}
