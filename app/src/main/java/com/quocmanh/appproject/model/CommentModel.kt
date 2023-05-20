package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class CommentModel {
    @SerializedName("message")
    val message : String? = ""
    @SerializedName("data")
    val data : MutableList<Comments>? = mutableListOf()
}