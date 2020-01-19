package com.alexkn.syntact.data.util

import androidx.room.TypeConverter

import com.alexkn.syntact.data.common.TemplateType

import java.time.Instant
import java.util.Locale

class Converters {

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(value) }

    @TypeConverter
    fun toLong(date: Instant?) = date?.toEpochMilli()

    @TypeConverter
    fun toLocale(value: String?) = value?.let { Locale(value) }

    @TypeConverter
    fun toString(locale: Locale?) = locale?.language

    @TypeConverter
    fun toTemplateType(value: String?) = value?.let(TemplateType::valueOf)

    @TypeConverter
    fun toString(templateType: TemplateType?) = templateType?.name
}



