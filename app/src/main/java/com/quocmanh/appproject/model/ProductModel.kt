package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ProductModel {
    @SerializedName("message")
    val message : String? = ""
    @SerializedName("data")
    val data : MutableList<Product>? = mutableListOf()
}