package com.alexkn.syntact.data.common

import com.google.gson.annotations.SerializedName

enum class TemplateType {

    @SerializedName("Basic")
    BASIC,

    @SerializedName("Advanced")
    ADVANCED,

    @SerializedName("Custom")
    CUSTOM
}
