package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val dataLogin : MutableList<DataLogin>? = mutableListOf()
}