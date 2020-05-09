package dev.alexknittel.syntact.core.common.util

import java.time.Instant
import java.time.ZoneId

fun Instant.atEndOfDay(zoneId: ZoneId? = ZoneId.systemDefault()): Instant =
        atZone(zoneId).withHour(23).withMinute(59).withSecond(59).toInstant()

fun Instant.atStartOfDay(zoneId: ZoneId? = ZoneId.systemDefault()): Instant =
        atZone(zoneId).withHour(0).withMinute(0).withSecond(0).toInstant()