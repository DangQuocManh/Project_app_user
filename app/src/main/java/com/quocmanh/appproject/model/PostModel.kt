package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class PostModel {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val data : MutableList<Post> = mutableListOf()
}