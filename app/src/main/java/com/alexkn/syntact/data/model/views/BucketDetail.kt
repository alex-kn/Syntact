package com.alexkn.syntact.data.model.views

import androidx.room.DatabaseView
import com.alexkn.syntact.data.common.Identifiable
import java.time.Instant
import java.util.*

@DatabaseView("SELECT b.name, b.id, b.language, b.createdAt,b.itemCount, (SELECT count(*) FROM solvableitem s WHERE s.timesSolved > 0 AND s.bucketId = b.id) as solvedCount, (SELECT count(*) FROM solvableitem s WHERE s.bucketId = b.id) as onDeviceCount FROM Bucket b;")
data class BucketDetail(
        override var id: Long,
        var name: String,
        var language: Locale,
        var createdAt: Instant,
        var itemCount: Int,
        var solvedCount: Int,
        var onDeviceCount: Int
) : Identifiable<Long>
