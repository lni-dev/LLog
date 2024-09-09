/*
 * Copyright (c) 2024 Linus Andera all rights reserved
 */

package de.linusdev.llog.replacer.datetime;

import de.linusdev.llog.replacer.ReplaceObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeObject implements ReplaceObject {

    private final static @NotNull DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private final @NotNull LocalDateTime dateTime;

    public DateTimeObject(@NotNull LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public @Nullable ReplaceObject getObject(@NotNull String name) {
        return null;
    }

    @Override
    public @Nullable String getProperty(@NotNull String name) {
        return switch (name) {
            case "day" -> "" + dateTime.getDayOfMonth();
            case "month" -> "" + dateTime.getMonthValue();
            case "year" -> "" + dateTime.getYear();
            case "second" -> "" + dateTime.getSecond();
            case "minute" -> "" + dateTime.getMinute();
            case "hour" -> "" + dateTime.getHour();
            default -> DateTimeFormatter.ofPattern(name).format(dateTime);
        };
    }

    @Override
    public String toString() {
        return formater.format(dateTime);
    }
}
