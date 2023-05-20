package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ModelOrderDetail {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val dataList : MutableList<OrderDetail> = mutableListOf()
}