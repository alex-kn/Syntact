package dev.alexknittel.syntact.data.common.util

import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

class Converters {

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(value) }

    @TypeConverter
    fun toLong(date: Instant?) = date?.toEpochMilli()

    @TypeConverter
    fun toLocale(value: String?) = value?.let { Locale(value) }

    @TypeConverter
    fun toString(locale: Locale?) = locale?.language
}



