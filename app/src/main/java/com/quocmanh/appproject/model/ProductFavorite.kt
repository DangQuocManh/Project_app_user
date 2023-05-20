package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class ProductFavorite (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("name_product")
    val nameProduct : String? = "",
    @SerializedName("image")
    val image : String? = "",
    @SerializedName("price")
    val price : String? = "",
    @SerializedName("id_user")
    val idUser : Int? = null,
    @SerializedName("id_product")
    val idProduct : Int? = null
)