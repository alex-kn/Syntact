package com.alexkn.syntact.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexkn.syntact.data.common.Identifiable
import java.time.Instant
import java.util.*

@Entity
data class Bucket(
        @PrimaryKey(autoGenerate = true) override var id: Long = 0,
        var language: Locale,
        var createdAt: Instant = Instant.now(),
        var userLanguage: Locale,
        var phrasesUrl: String,
        var itemCount: Int = 0
) : Identifiable
