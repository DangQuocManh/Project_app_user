package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class DataLogin(
    @SerializedName("id_user")
    val id: Int? = null,

    @SerializedName("avatar")
    var avatar: String? = "",

    @SerializedName("username")
    var userName: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("address")
    var address: String? = "",

    @SerializedName("type")
    var type: String = ""
)
