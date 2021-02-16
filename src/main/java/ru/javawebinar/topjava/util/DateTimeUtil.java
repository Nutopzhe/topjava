package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    public static boolean isBetweenHalfOpen(LocalTime lt, LocalDate startTime, LocalDate endTime) {
//        return lt.compareTo(LocalTime.from(startTime)) >= 0 && lt.compareTo(LocalTime.from(endTime)) < 0;
//    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen (T current, T start, T end) {
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

