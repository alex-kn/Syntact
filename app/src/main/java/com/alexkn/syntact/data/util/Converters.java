package com.alexkn.syntact.data.util;

import com.alexkn.syntact.domain.common.GameMode;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.restservice.TemplateType;

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

    @TypeConverter
    public static GameMode toGameMode(String value) {

        return value == null ? null : GameMode.valueOf(value);
    }

    @TypeConverter
    public static String toString(GameMode gameMode) {

        return gameMode == null ? null : gameMode.name();
    }
}



