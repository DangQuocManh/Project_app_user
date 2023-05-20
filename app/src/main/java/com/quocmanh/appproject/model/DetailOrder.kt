package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class DetailOrder(
    @SerializedName("id")
    val idDetailOrder : Int? = null,
    @SerializedName("name_user")
    val nameUser : String = "",
    @SerializedName("email")
    val email : String = "",
    @SerializedName("phone_user")
    val phoneUser : String = "",
    @SerializedName("detail")
    val detail : String = "",
    @SerializedName("address")
    val address : String = "",
    @SerializedName("status")
    val status : String = ""
)