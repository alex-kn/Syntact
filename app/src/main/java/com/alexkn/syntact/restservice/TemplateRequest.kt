package com.alexkn.syntact.restservice

import com.google.gson.annotations.SerializedName

import java.util.Locale

data class TemplateRequest(
        var name: String,
        @SerializedName("template_type") var templateType: TemplateType,
        var language: Locale
)

