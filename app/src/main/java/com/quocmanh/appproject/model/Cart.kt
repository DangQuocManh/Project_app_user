package com.quocmanh.appproject.model

data class Cart(val id : Int, val name_product : String, val image : String, var price : String,
                var count_product : Int, val id_user : Int?, val id_product : Int?)