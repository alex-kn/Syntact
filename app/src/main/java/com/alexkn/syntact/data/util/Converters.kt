package com.alexkn.syntact.data.util

import androidx.room.TypeConverter

import com.alexkn.syntact.restservice.TemplateType

import java.time.Instant
import java.util.Locale

class Converters {

    @TypeConverter
    fun toInstant(value: Long?): Instant? {

        return if (value == null) null else Instant.ofEpochMilli(value)
    }

    @TypeConverter
    fun toLong(date: Instant?): Long? {

        return date?.toEpochMilli()
    }

    @TypeConverter
    fun toLocale(value: String?): Locale? {

        return if (value == null) null else Locale(value)
    }

    @TypeConverter
    fun toString(locale: Locale?): String? {

        return locale?.language
    }

    @TypeConverter
    fun toTemplateType(value: String?): TemplateType? {

        return if (value == null) null else TemplateType.valueOf(value)
    }

    @TypeConverter
    fun toString(templateType: TemplateType?): String? {

        return templateType?.name
    }
}



