package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName
import java.io.File

data class RequestChangeAvatar (
    @SerializedName("images")
    val file : File?,
    @SerializedName("idUser")
    val idUser : String
)