package com.alexkn.syntact.restservice

import com.alexkn.syntact.data.common.Identifiable
import com.google.gson.annotations.SerializedName

import java.util.Locale

data class TemplateResponse(
        override var id: Long,
        var name: String,
        @SerializedName("template_type") var templateType: TemplateType,
        var language: Locale,
        @SerializedName("phrases") var phrasesUrl: String,
        @SerializedName("phrases_count") var count: Int,
        var description: String
) : Identifiable<Long>