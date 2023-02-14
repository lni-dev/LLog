/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.base.data;

import org.jetbrains.annotations.NotNull;

public interface LogData {

    /**
     * The {@link ContentType} of this {@link LogData}.
     * @return {@link ContentType}
     */
    @NotNull ContentType getContentType();

    /**
     * If this method returns {@code true}, {@link #generateString()} should never throw
     * an exception.
     * @return {@code false} if {@link #generateString()} will throw an {@link UnsupportedOperationException}, {@code true} otherwise.
     */
    boolean canGenerateString();

    /**
     *
     * @return {@link LogData} as {@link String}.
     * @throws UnsupportedOperationException if this {@link LogData} cannot be converted to a String.
     * @see #canGenerateString()
     */
    @NotNull String generateString() throws UnsupportedOperationException;

    /**
     * If this method returns {@code true}, {@link #generateBytes()} should never throw
     * an exception.
     * @return {@code false} if {@link #generateBytes()} will throw an {@link UnsupportedOperationException}, {@code true} otherwise.
     */
    boolean canGenerateBytes();

    /**
     *
     * @return {@link LogData} as byte array.
     * @throws UnsupportedOperationException if this {@link LogData} cannot be converted to a byte array.
     * @see #canGenerateBytes()
     */
    byte @NotNull [] generateBytes()  throws UnsupportedOperationException;

    /**
     * if this method returns {@code true}, {@link #getCustomObject()} should not throw an exception.
     * @return {@code false} if {@link #getCustomObject()} will throw an {@link UnsupportedOperationException}, {@code true} otherwise.
     */
    boolean hasCustomObject();

    /**
     *
     * @return {@link LogData}s custom object. The type will be defined by the given {@link #getContentType() content type}.
     * @throws UnsupportedOperationException if this {@link LogData} does not contain a custom Object.
     * @see #hasCustomObject()
     */
    @NotNull Object getCustomObject();

}
