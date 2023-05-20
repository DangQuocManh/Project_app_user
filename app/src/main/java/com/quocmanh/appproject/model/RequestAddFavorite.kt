package com.quocmanh.appproject.model

data class RequestAddFavorite( val namePro : String, val imagePro : String, val price : String,
                      val idUser : Int, val idProduct : Int) {
}