package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("avatar")
    val avatar : String? = ""
) {
}