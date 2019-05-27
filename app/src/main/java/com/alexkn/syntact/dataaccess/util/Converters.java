package com.alexkn.syntact.dataaccess.util;

import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.common.TemplateType;

import java.time.Instant;
import java.util.Locale;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static Instant toInstant(Long value) {

        return value == null ? null : Instant.ofEpochMilli(value);
    }

    @TypeConverter
    public static Long toLong(Instant date) {

        return date == null ? null : date.toEpochMilli();
    }

    @TypeConverter
    public static Locale toLocale(String value) {

        return value == null ? null : new Locale(value);
    }

    @TypeConverter
    public static String toString(Locale locale) {

        return locale == null ? null : locale.getLanguage();
    }

    @TypeConverter
    public static LetterColumn toLetterColumn(String value) {

        return value == null ? null : LetterColumn.valueOf(value);
    }

    @TypeConverter
    public static String toString(LetterColumn letterColumn) {

        return letterColumn == null ? null : letterColumn.name();
    }

    @TypeConverter
    public static TemplateType toTemplateType(String value) {

        return value == null ? null : TemplateType.valueOf(value);
    }

    @TypeConverter
    public static String toString(TemplateType templateType) {

        return templateType == null ? null : templateType.name();
    }
}



