package com.quocmanh.appproject.myinterface

import com.quocmanh.appproject.model.*
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("api/v1/getAllProducts")
    fun getAllProduct() : Call<ProductModel>

    @POST("api/v1/createNewUser")
    fun register(@Body requestRegister: RequestRegister) : Call<ResponseRegister>

    @POST("api/v1/loginUser")
    fun loginUser(@Body loginResquest: LoginResquest) : Call<LoginResponse>

    @POST("api/v1/getDataProductFromId")
    fun getProductFromId(@Body requestId : RequestId) : Call<ResponseGetProFromId>

    @POST("api/v1/insertCart")
    fun insertProductToCart(@Body requestInsertCart : RequestInsertCart) : Call<ResponseInsertCart>

    @POST("api/v1/insertFavoriteProduct")
    fun insertFavoriteProduct(@Body requestAddFavorite: RequestAddFavorite) : Call<ResponseInsertCart>

//    @POST("/api/v1/changeAvatarUser")
//    fun changeAvatar(@Body requestChangeAvatar: RequestChangeAvatar) : Call<ResponseChangeAvatar>

    @Multipart
    @POST("api/v1/changeAvatarUser")
    fun updateAvatar(
        @Part images : MultipartBody.Part,
        @Part("idUser") idUser : RequestBody
    ) : Call<ResponseChangeAvatar>

    @Multipart
    @POST("/api/v1/addPost")
    fun addPost(
        @Part images : MultipartBody.Part,
        @Part("title") title : RequestBody,
        @Part("content") content : RequestBody,
        @Part("time") time : RequestBody,
        @Part("idUser") idUser : RequestBody
    ) : Call<ResponseChangeAvatar>
}