package com.alexkn.syntact.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = SolvableItem::class, parentColumns = ["id"], childColumns = ["clueSolvableItemId"], onDelete = CASCADE)])
data class Clue(

        @PrimaryKey
        @ColumnInfo(name = "clueId")
        var id: Long,

        @ColumnInfo(name = "clueText")
        var text: String,

        @ColumnInfo(name = "clueSolvableItemId", index = true)
        var solvableItemId: Long

)
