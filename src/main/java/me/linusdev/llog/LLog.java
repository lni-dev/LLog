package me.linusdev.llog;

import me.linusdev.llog.base.LogInstance;
import me.linusdev.llog.base.LogSource;
import me.linusdev.llog.base.Logger;
import me.linusdev.llog.base.impl.StandardLogInstance;
import me.linusdev.llog.impl.nop.NOPLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class LLog {

    public static @NotNull Logger logger = new NOPLogger();

    public static final @NotNull StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Creates a new {@link LogInstance} for the calling class.
     * <br><br>
     * {@link LogInstance} should only retrieved a single time for a class and saved in a static variable.
     * @return {@link LogInstance} for the calling class
     */
    public static @NotNull LogInstance getLogInstance() {
        Class<?> caller = STACK_WALKER.getCallerClass();
        return new StandardLogInstance(logger, caller);
    }

    /**
     * Creates a new {@link LogInstance} for given source and with given information.
     * @param source the name of the log source. see {@link LogSource#getName()}
     * @param information see {@link LogSource#getAdditionalInformation()}
     * @return a new {@link LogSource} for the given source.
     */
    public static @NotNull LogInstance getLogInstance(@NotNull String source, @Nullable Map<String, String> information) {
        return new StandardLogInstance(logger, source, information);
    }

    /**
     * The currently used {@link Logger}.<br><br>
     * You are probably looking for {@link #getLogInstance()}.
     * @return current {@link Logger}
     * @see #getLogInstance()
     */
    public static @NotNull Logger getLogger() {
        return logger;
    }


}
