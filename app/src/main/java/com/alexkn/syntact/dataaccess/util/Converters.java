package com.alexkn.syntact.dataaccess.util;

import java.time.Instant;
import java.util.Locale;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Instant fromTimestamp(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Instant date) {
        return date == null ? null : date.toEpochMilli();
    }

    @TypeConverter
    public static Locale fromString(String value) {
        return value == null ? null : new Locale(value);
    }

    @TypeConverter
    public static String localeToString(Locale locale) {
        return locale == null ? null : locale.getLanguage();
    }
}



