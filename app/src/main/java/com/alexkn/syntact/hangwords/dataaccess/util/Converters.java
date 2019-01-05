package com.alexkn.syntact.hangwords.dataaccess.util;

import java.time.Instant;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class Converters {
    @TypeConverter
    public static Instant fromTimestamp(Long value) {
        return value == null ? null : Instant.ofEpochMilli(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Instant date) {
        return date == null ? null : date.toEpochMilli();
    }
}



