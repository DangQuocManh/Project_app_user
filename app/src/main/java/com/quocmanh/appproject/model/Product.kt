package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("product_code")
    val productCode : String? = "",
    @SerializedName("brand_code")
    val brandCode : String? = "",
    @SerializedName("name_product")
    val nameProduct : String? = "",
    @SerializedName("image")
    val image : String? = "",
    @SerializedName("price")
    val price : String? = "",
    @SerializedName("product_parameters")
    val productParameter : String? = "",
    @SerializedName("description")
    val description : String? = ""
)