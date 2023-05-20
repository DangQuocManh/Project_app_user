package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ResponseGetProFromId {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val dataProduct : MutableList<Product> = mutableListOf()
}