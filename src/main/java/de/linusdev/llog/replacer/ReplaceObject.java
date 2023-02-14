/*
 * Copyright (c) 2023 Linus Andera all rights reserved
 */

package de.linusdev.llog.replacer;

import de.linusdev.llog.base.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a {@link ReplaceObject} may be used in LLog-Replaceable-Strings.
 * For Example in the {@code llog.properties} file.
 * <br><br>
 * The {@link ReplaceObject} can be accessed with the following syntax:<br>
 * <pre>      {@code {self.location.parent}}</pre>
 * Each {@link ReplaceObject} is separated by a dot. In the given example {@code self} and {@code location} are both
 * {@link ReplaceObject}s. {@code parent} may be a {@link ReplaceObject} or a simple {@link #getProperty(String) property}.
 * If {@code parent} is a {@link ReplaceObject} its {@link Object#toString() toString()} method will be called to retrieve the value
 * with which the replaceable will be replaced.
 * <br><br>
 * {@link ReplaceObject}s may be added in your {@code adjustReplacer(LLogStringReplacer)} method.
 * of your {@link Logger} implementation. More information about this can be viewed in the {@link Logger} docs.
 */
public interface ReplaceObject {

    @Nullable ReplaceObject getObject(@NotNull String name);

    @Nullable String getProperty(@NotNull String name);

}
