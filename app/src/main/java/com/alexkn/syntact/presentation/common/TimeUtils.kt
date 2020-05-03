package com.alexkn.syntact.presentation.common

import org.apache.commons.lang3.time.DurationFormatUtils
import java.time.Duration

fun Duration.toUiString(): String {
    return when {
        isNegative || isZero -> "now"
        toDays() > 1 -> DurationFormatUtils.formatDuration(toMillis(), "d' Days'", false)
        toDays() > 0 -> DurationFormatUtils.formatDuration(toMillis(), "d' Day'", false)
        toHours() > 1 -> DurationFormatUtils.formatDuration(toMillis(), "H' Hours'", false)
        toHours() > 0 -> DurationFormatUtils.formatDuration(toMillis(), "H' Hour'", false)
        toMinutes() > 1 -> DurationFormatUtils.formatDuration(toMillis(), "m' Minutes'", false)
        toMinutes() > 0 -> DurationFormatUtils.formatDuration(toMillis(), "m' Minute'", false)
        toMillis() >= 2000 -> DurationFormatUtils.formatDuration(toMillis(), "s' Seconds'", false)
        else -> DurationFormatUtils.formatDuration(toMillis(), "s' Second'", false)
    }
}