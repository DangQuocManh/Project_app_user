package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ResponseProductFavorite {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val dataCart : MutableList<ProductFavorite> = mutableListOf()
}