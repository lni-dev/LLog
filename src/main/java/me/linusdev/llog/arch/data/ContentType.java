package me.linusdev.llog.arch.data;

import org.jetbrains.annotations.NotNull;

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

    default @NotNull String generateFormattedString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getTopLevel()).append("/").append(getSubLevel());

        for(ContentTypeAttribute attribute : getAttributes())
            sb.append(" ").append(attribute.getKey()).append("=").append(attribute.getValue());

        return sb.toString();
    }

}
