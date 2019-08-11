package com.alexkn.syntact.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alexkn.syntact.data.common.Identifiable

@Entity(foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["templateId"], onDelete = ForeignKey.CASCADE)])
data class Phrase(
        @PrimaryKey override var id: Long,
        var text: String,
        @ColumnInfo(index = true) var templateId: Long
) : Identifiable<Long>
