package com.alexkn.syntact.data.model.views

import androidx.room.DatabaseView
import com.alexkn.syntact.data.common.Identifiable
import java.time.Instant
import java.util.*

@DatabaseView("SELECT b.id, b.language, b.createdAt,b.itemCount, (SELECT count(*) FROM solvableitem s JOIN bucket b  ON s.bucketId = b.id WHERE s.timesSolved > 0) as solvedCount FROM Bucket b;")
data class BucketDetail(
        override var id: Long,
        var language: Locale,
        var createdAt: Instant,
        var itemCount: Int,
        var solvedCount: Int
) : Identifiable
