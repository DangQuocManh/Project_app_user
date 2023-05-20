package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ProductModel1 {
    @SerializedName("message")
    val message : String? = ""
    @SerializedName("data")
    val data : ArrayList<Product>? = arrayListOf()
}