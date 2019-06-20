package com.alexkn.syntact.restservice;

import com.google.gson.annotations.SerializedName;

public enum TemplateType {

    @SerializedName("Basic")
    BASIC,

    @SerializedName("Advanced")
    ADVANCED,

    @SerializedName("Custom")
    CUSTOM
}
