package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class OrderDetail (
    @SerializedName("id")
    val id : Int,
    @SerializedName("name_user")
    val nameUser : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("phone_user")
    val phone : String,
    @SerializedName("detail")
    val detail : String,
    @SerializedName("price")
    val price : String,
    @SerializedName("address")
    val address : String,
    @SerializedName("date_create")
    val dateCreate : String,
    @SerializedName("status")
    val status : String
) {
}