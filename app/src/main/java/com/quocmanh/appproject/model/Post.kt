package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id : Int,
    @SerializedName("image_post")
    val imagePost : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("content")
    val content : String,
    @SerializedName("time_create")
    val timeCreate : String,
    @SerializedName("id_user")
    val idUser : Int
)