package com.alexkn.syntact.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.alexkn.syntact.data.common.Identifiable
import java.time.Instant

@Entity(foreignKeys = [ForeignKey(entity = Bucket::class, parentColumns = ["id"], childColumns = ["bucketId"], onDelete = CASCADE)])
data class SolvableItem(

        @PrimaryKey
        override var id: Long,

        @ColumnInfo(name = "solvableItemText")
        var text: String,

        var lastSolved: Instant? = null,

        var nextDueDate: Instant? = null,

        var easiness: Float = 2.5f,

        var consecutiveCorrectAnswers: Int = 0,

        var timesSolved: Int = 0,

        var disabled: Boolean = false,

        @ColumnInfo(index = true)
        var bucketId: Long

) : Identifiable<Long>
