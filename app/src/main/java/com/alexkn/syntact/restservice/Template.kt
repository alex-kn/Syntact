package com.alexkn.syntact.restservice

import com.google.gson.annotations.SerializedName

import java.util.Locale

data class Template(
        var id: String,
        var name: String,
        @SerializedName("template_type") var templateType: TemplateType,
        var language: Locale,
        @SerializedName("phrases") var phrasesUrl: String,
        @SerializedName("phrases_count") var count: Int
)