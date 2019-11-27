package com.alexkn.syntact.data.common

import com.google.gson.annotations.SerializedName

enum class TemplateType(val type: String) {

    @SerializedName(value = "Basic", alternate = ["basic", "BASIC"])
    BASIC("Basic"),

    @SerializedName(value = "Advanced", alternate = ["advanced", "ADVANCED"])
    ADVANCED("Advanced"),

    @SerializedName("Custom", alternate = ["custom", "CUSTOM"])
    CUSTOM("Custom")
}
