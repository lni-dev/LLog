package me.linusdev.llog.base.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnnecessaryModifier")
public interface ContentTypeAttribute {

    public class Impl implements ContentTypeAttribute {

        private final @NotNull String key;
        private final @NotNull String value;

        public Impl(@NotNull String key, @NotNull String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public @NotNull String getKey() {
            return key;
        }

        @Override
        public @NotNull String getValue() {
            return value;
        }
    }

    public static @NotNull String CHARSET_ATTRIBUTE_KEY = "charset";

    /**
     * Creates a new {@link ContentTypeAttribute} of given key and value.
     * @param key attribute key
     * @param value attribute value
     * @return {@link ContentTypeAttribute}
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull ContentTypeAttribute of(@NotNull String key, @NotNull String value) {
        return new Impl(key, value);
    }

    /**
     *
     * @return key of the content type attribute
     */
    @NotNull String getKey();

    /**
     *
     * @return value of the content type attribute
     */
    @NotNull String getValue();

}
