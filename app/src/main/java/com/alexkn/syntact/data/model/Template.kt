package com.alexkn.syntact.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexkn.syntact.data.common.Identifiable
import com.alexkn.syntact.restservice.TemplateType
import java.util.*

@Entity
data class Template(
        @PrimaryKey override val id: Long,
        var name: String,
        var templateType: TemplateType,
        var language: Locale,
        var phrasesUrl: String,
        var count: Int,
        var description: String
) : Identifiable<Long>