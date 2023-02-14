/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.data;

import de.linusdev.llog.base.impl.StandardContentType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @see StandardContentType
 */
@SuppressWarnings("unused")
public interface ContentType {

    /**
     *
     * @return top level content-type.
     */
    @NotNull String getTopLevel();

    /**
     *
     * @return sub level content-type.
     */
    @NotNull String getSubLevel();

    /**
     *
     * @return array of {@link ContentTypeAttribute attributes}.
     */
    @NotNull ContentTypeAttribute[] getAttributes();

    /**
     * Generates a HTTP header ready string.
     * @return formatted String for this {@link ContentType}
     */
    default @NotNull String generateFormattedString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getTopLevel()).append("/").append(getSubLevel());

        for(ContentTypeAttribute attribute : getAttributes())
            sb.append(" ").append(attribute.getKey()).append("=").append(attribute.getValue());

        return sb.toString();
    }

    /**
     * Checks if this {@link ContentType} is the same as the given one. Attributes are ignored.
     * @param other {@link ContentType} to check with.
     * @return {@code true} if they are the same {@link ContentType}. {@code false} otherwise.
     */
    default boolean is(@NotNull ContentType other) {
        return Objects.equals(getTopLevel(), other.getTopLevel()) && Objects.equals(getSubLevel(), other.getSubLevel());
    }

}
