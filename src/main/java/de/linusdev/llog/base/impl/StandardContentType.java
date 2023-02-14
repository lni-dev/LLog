/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.impl;

import de.linusdev.llog.base.data.ContentType;
import de.linusdev.llog.base.data.ContentTypeAttribute;
import org.jetbrains.annotations.NotNull;

/**
 * A few {@link ContentType}s, which may be useful.
 */
public enum StandardContentType implements ContentType {

    TEXT_PLAIN_UTF_8("text", "plain", ContentTypeAttribute.of(ContentTypeAttribute.CHARSET_ATTRIBUTE_KEY, "utf-8")),

    APPLICATION_JSON("application", "json"),

    CUSTOM_OBJECT_THROWABLE("custom-object", "throwable"),

    ;

    private final @NotNull String topLevel;
    private final @NotNull String subLevel;
    private final @NotNull ContentTypeAttribute @NotNull [] attributes;

    StandardContentType(@NotNull String topLevel, @NotNull String subLevel, @NotNull ContentTypeAttribute @NotNull ... attributes) {
        this.topLevel = topLevel;
        this.subLevel = subLevel;
        this.attributes = attributes;
    }

    @Override
    public @NotNull String getTopLevel() {
        return topLevel;
    }

    @Override
    public @NotNull String getSubLevel() {
        return subLevel;
    }

    @Override
    public @NotNull ContentTypeAttribute[] getAttributes() {
        return attributes;
    }
}
