package com.alexkn.syntact.data.common

import com.google.gson.annotations.SerializedName

enum class TemplateType(val type: String) {

    @SerializedName("Basic")
    BASIC("Basic"),

    @SerializedName("Advanced")
    ADVANCED("Advanced"),

    @SerializedName("Custom")
    CUSTOM("Custom")
}
