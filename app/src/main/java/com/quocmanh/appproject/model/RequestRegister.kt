package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class RequestRegister(
    @SerializedName("avatar")
    val avatar : String?,
    @SerializedName("userName")
    val userName : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("address")
    val address : String?,
    @SerializedName("date_create")
    val dateCreate: String?,
    @SerializedName("type")
    val type : String
)