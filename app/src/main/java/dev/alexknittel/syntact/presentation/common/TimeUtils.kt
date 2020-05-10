package dev.alexknittel.syntact.presentation.common

import org.apache.commons.lang3.time.DurationFormatUtils
import java.time.Duration

fun Duration.toUiString(): String {
    return when {
        isNegative || isZero -> "today"
        toDays() > 1 -> DurationFormatUtils.formatDuration(toMillis(), "in d' Days'", false)
        toDays() > 0 -> "tomorrow"
        else -> "today"
    }
}