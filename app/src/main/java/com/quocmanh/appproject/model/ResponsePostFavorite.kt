package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ResponsePostFavorite {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val dataList : MutableList<PostFavorite> = mutableListOf()
}