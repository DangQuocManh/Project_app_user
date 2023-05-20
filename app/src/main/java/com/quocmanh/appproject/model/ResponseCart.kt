package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ResponseCart {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val dataCart : MutableList<Cart> = mutableListOf()
}