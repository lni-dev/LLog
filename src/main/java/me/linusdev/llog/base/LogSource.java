package me.linusdev.llog.base;

import me.linusdev.llog.base.data.LogData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * The source of a {@link LogData}.
 */
public interface LogSource {

    /**
     * Name of this {@link LogSource}.
     * @return name as {@link String}
     */
    @NotNull String getName();

    /**
     * The returned map should contain additional information about this {@link LogSource}.
     * This information may be omitted by some logging implementations.<br><br>
     * The key should be a describing name of the information given as value.
     * @return {@link Map} containing additional information
     */
    @Nullable Map<String, String> getAdditionalInformation();

}
