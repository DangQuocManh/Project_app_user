package com.quocmanh.appproject.model

import com.google.gson.annotations.SerializedName

class AvatarModel {
    @SerializedName("data")
    val data : MutableList<Avatar> = mutableListOf()
}