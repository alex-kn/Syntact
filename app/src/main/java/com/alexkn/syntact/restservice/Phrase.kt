package com.alexkn.syntact.restservice

import com.google.gson.annotations.SerializedName

import java.util.Locale

data class Phrase (
    var id: Long,
    var text: String,
    var language: Locale,
    @SerializedName("translations") var translationsUrl: String
)
