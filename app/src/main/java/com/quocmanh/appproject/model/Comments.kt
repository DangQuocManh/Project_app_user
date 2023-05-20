package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class Comments (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("id_user")
    val idUser: Int? = null,
    @SerializedName("id_product")
    val idProduct : Int? = null,
    @SerializedName("comment")
    val comment : String = "",
    @SerializedName("time_comment")
    val timeComment : String = "",
    @SerializedName("user_name")
    val userName : String = "",
    @SerializedName("avatar")
    val avatar : String = ""

)