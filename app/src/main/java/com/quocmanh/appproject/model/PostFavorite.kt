package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class PostFavorite(
    @SerializedName("id")
    val id : Int,
    @SerializedName("id_post")
    val idPost : Int,
    @SerializedName("id_user")
    val idUser : Int,
    @SerializedName("image")
    val image : Int,
    @SerializedName("title")
    val title : Int,
    @SerializedName("content")
    val content : Int,
    @SerializedName("time")
    val time : Int
) {
}