package me.linusdev.llog.arch.data;

import org.jetbrains.annotations.NotNull;

public interface ContentTypeAttribute {

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
