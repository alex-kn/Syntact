package com.alexkn.syntact.rest.to

import com.alexkn.syntact.data.common.TemplateType
import com.google.gson.annotations.SerializedName

import java.util.Locale

data class TemplateRequest(
        var name: String,
        var description: String,
        var source: Locale,
        var target: Locale,
        var words: List<String>
)

