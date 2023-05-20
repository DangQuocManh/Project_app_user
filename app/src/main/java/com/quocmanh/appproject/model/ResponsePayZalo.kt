package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class ResponsePayZalo {
    @SerializedName("message")
    val message : String = ""
    @SerializedName("data")
    val data : PayZalo = PayZalo(null)
}
