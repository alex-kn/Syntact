package com.alexkn.syntact.data.model.views

import androidx.room.DatabaseView
import com.alexkn.syntact.data.common.Identifiable
import java.time.Instant
import java.util.*

@DatabaseView("SELECT b.name, b.id, b.language, b.createdAt,b.itemCount, (SELECT count(*) FROM solvableitem s WHERE s.timesSolved > 0 AND s.disabled = 0 AND s.bucketId = b.id) as solvedCount, (SELECT count(*) FROM solvableitem s WHERE s.bucketId = b.id AND s.disabled = 0) as onDeviceCount,(SELECT b.itemCount - (SELECT count(*) FROM solvableitem s WHERE s.nextDueDate > (SELECT strftime('%s','now') || substr(strftime('%f','now'),4)) AND s.bucketId = b.id) - (SELECT count(*) FROM solvableitem s WHERE s.disabled = 1 AND s.nextDueDate <= (SELECT strftime('%s','now') || substr(strftime('%f','now'),4)) AND s.bucketId = b.id)) as dueCount,  (SELECT count(*) FROM solvableitem s WHERE s.disabled = 1 AND s.bucketId = b.id) as disabledCount FROM Bucket b;")
data class BucketDetail(
        override var id: Long,
        var name: String,
        var language: Locale,
        var createdAt: Instant,
        var itemCount: Int,
        var solvedCount: Int,
        var onDeviceCount: Int,
        var dueCount: Int,
        var disabledCount: Int
) : Identifiable<Long>
