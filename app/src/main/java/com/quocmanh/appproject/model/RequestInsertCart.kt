package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

data class RequestInsertCart (
    val nameProduct : String,
    val imageProduct : String,
    val price : String,
    val countProduct : String,
    val idUser : String,
    val idProduct : String
)