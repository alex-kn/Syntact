package com.alexkn.syntact.service.to

import com.google.gson.annotations.SerializedName

import java.util.Locale

data class PhraseResponse (
    var id: Long,
    var text: String,
    var language: Locale,
    @SerializedName("translations") var translationsUrl: String
)
