package me.linusdev.llog.base.data;

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

    boolean canGenerateBytes();

    byte @NotNull [] generateBytes()  throws UnsupportedOperationException;

}
