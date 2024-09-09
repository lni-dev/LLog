/*
 * Copyright (c) 2023-2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.replacer;

import de.linusdev.llog.LLog;
import de.linusdev.llog.replacer.datetime.DateTimeObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LLogStringReplacer implements ReplaceObject {

    private final @NotNull HashMap<String, ReplaceObject> objects;

    public LLogStringReplacer() {
        this.objects = new HashMap<>();

        MutableReplaceObject self = new MutableReplaceObject();

        Path path = null;
        try {
            path = Paths.get(LLog.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            System.err.println("llog: Cannot get jar path.");
        }

        self.addObject("location", new LocationObject(path));

        addObject("self", self);
        addObject("datetime", new DateTimeObject(LocalDateTime.now(ZoneId.systemDefault())));
    }

    public @NotNull String process(@NotNull String input) {
        Pattern pattern = Pattern.compile("\\{[^{}]+}");
        Matcher matcher = pattern.matcher(input);

        while(matcher.find()) {
            String matched = matcher.group();
            matched = matched.substring(1, matched.length()-1);

            input = input.substring(0, matcher.start()) + replace(matched) + input.substring(matcher.end());

            matcher = pattern.matcher(input);
        }

        return input;
    }

    private @NotNull String replace(@NotNull String toReplace) {

        String[] names = toReplace.split("\\.");

        ReplaceObject current = this;
        String value = null;

        for(String name : names) {
            if(current == null) {
                value = null;
                break;
            }

            ReplaceObject object = current.getObject(name);
            if(object == null) {
                value = current.getProperty(name);
                current = null;
            } else {
                current = object;
                value = current.toString();
            }
        }

        return Objects.toString(value);
    }

    @Override
    public @Nullable ReplaceObject getObject(@NotNull String name) {
        return this.objects.get(name);
    }

    @SuppressWarnings("UnusedReturnValue")
    public @Nullable LLogStringReplacer addObject(@NotNull String name, @NotNull ReplaceObject object) {
        objects.put(name, object);
        return this;
    }

    @Override
    public @Nullable String getProperty(@NotNull String name) {
        return null;
    }
}
