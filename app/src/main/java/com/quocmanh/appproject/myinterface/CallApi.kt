package com.quocmanh.appproject.myinterface

import com.quocmanh.appproject.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CallApi {
    @GET("/api/v1/getAllProducts")
    fun getAllProduct(

    ) : Observable<ProductModel>

    @GET("/api/v1/getAllProducts")
    fun getAllProduct1(

    ) : Observable<ProductModel1>

    @POST("/api/v1/insertCart")
    @FormUrlEncoded
    fun insertCart(
//        @Body requestInsertCart: RequestInsertCart
        @Field("nameProduct") namePro : String,
        @Field("imageProduct") imagePro : String,
        @Field("price") price : String,
        @Field("countProduct") count : Int,
        @Field("idUser") idUser : Int,
        @Field("idProduct") idPro : Int
    ) : Observable<ResponseInsertCart>

    @POST("api/v1/insertFavoriteProduct")
    @FormUrlEncoded
    fun insertFavorite(
//        @Body requestInsertCart: RequestInsertCart
        @Field("nameProduct") namePro : String,
        @Field("imageProduct") imagePro : String,
        @Field("price") price : String,
        @Field("idUser") idUser : Int,
        @Field("idProduct") idPro : Int
    ) : Observable<ResponseInsertCart>

    @POST("api/v1/getDataCart")
    @FormUrlEncoded
    fun getDataCart(
        @Field("idUser") idUser : Int
    ) : Observable<ResponseCart>

    @POST("api/v1/getDataFavorite")
    @FormUrlEncoded
    fun getDataFavorite(
        @Field("idUser") idUser : Int
    ) : Observable<ResponseProductFavorite>

    @POST("/api/v1/getDataProductFromId")
    @FormUrlEncoded
    fun getDataProFromId(
        @Field("idPro") idPro : Int
    ) : Observable<ProductModel>

    @POST("api/v1/updateCart")
    @FormUrlEncoded
    fun updateCart(
        @Field("price") price : String,
        @Field("countProduct") countProduct: Int,
        @Field("idUser") idUser : Int,
        @Field("idProduct") idProduct : Int
    ) : Observable<ResponseInsertCart>

    @POST("api/v1/deleteItemCart")
    @FormUrlEncoded
    fun deleteItemCart(
        @Field("idUser") idUser : Int,
        @Field("idProduct") idProduct : Int
    ) : Observable<ResponseInsertCart>

    @POST("api/v1/getDataUser")
    @FormUrlEncoded
    fun getDataUser(
        @Field("idUser") idUser : Int
    ) : Observable<LoginResponse>

    @POST("api/v1/updateInforUser")
    @FormUrlEncoded
    fun updateInforUser(
        @Field("userName") userName : String,
        @Field("email") email : String,
        @Field("phone") phone : String,
        @Field("address") address: String,
        @Field("idUser") idUser : Int
    ) : Observable<ResponseInsertCart>

    @POST("api/v1/updatePassword")
    @FormUrlEncoded
    fun updatePassword(
        @Field("idUser") idUser : Int,
        @Field("password") password : String,
        @Field("passNew") passNew : String
    ) : Observable<ResponseInsertCart>

    @POST("api/v1/getDataSearch")
    @FormUrlEncoded
    fun getDataSearchAdvanced(
        @Field("typeProduct") typeProduct : String,
        @Field("brandProduct") brandProduct : String,
        @Field("price") price : String
    ) : Observable<ProductModel>

    @POST("api/v1/addComments")
    @FormUrlEncoded
    fun addComments(
        @Field("idUser") idUser : Int,
        @Field("idProduct") idPro : Int,
        @Field("comment") comment : String,
        @Field("time_comment") timeComment : String,
        @Field("username") userName : String,
        @Field("avatar") avatar : String
    ) : Observable<ModelComments>

    @POST("/api/v1/getDataComments")
    @FormUrlEncoded
    fun getComments(
        @Field("idProduct") idPro : Int
    ) : Observable<CommentModel>

    @POST("/api/v1/addDetailOrder")
    @FormUrlEncoded
    fun addDetailOrder(
        @Field("nameUser") nameUser : String,
        @Field("emailUser") emailUser : String,
        @Field("phoneUser") phoneUser : String,
        @Field("detail") detail : String,
        @Field("price") price : String,
        @Field("address") address : String,
        @Field("date_create") dateCreate : String,
        @Field("status") status : String
    ) : Observable<ResponseInsertCart>

    @POST("/api/v1/addDetailOrderZalo")
    @FormUrlEncoded
    fun addDetailOrderZalo(
        @Field("nameUser") nameUser : String,
        @Field("emailUser") emailUser : String,
        @Field("phoneUser") phoneUser : String,
        @Field("detail") detail : String,
        @Field("price") price : String,
        @Field("address") address : String,
        @Field("date_create") dateCreate : String,
        @Field("status") status : String
    ) : Observable<ResponsePayZalo>

    @POST("/api/v1/updateTokenZaloPay")
    @FormUrlEncoded
    fun updateTokenZalo(
        @Field("idOrder") idOrder : String,
        @Field("token") token : String,
    ) : Observable<ResponseInsertCart>

    @POST("/api/v1/getFromProductCode")
    @FormUrlEncoded
    fun getFromProductCode(
        @Field("productCode") productCode : String,
    ) : Observable<ProductModel>

    @POST("/api/v1/getAvatarFromId")
    @FormUrlEncoded
    fun getAvatarFromId(
        @Field("idUser") idUser : Int
    ) : Observable<AvatarModel>

    @GET("/api/v1/getAllPost")
    fun getAllPost(

    ) : Observable<PostModel>

    @POST("/api/v1/addPostFavorite")
    @FormUrlEncoded
    fun addPostFavorite(
        @Field("id_post") idPost : Int,
        @Field("id_user") idUser : Int,
        @Field("image") image : String,
        @Field("title") title : String,
        @Field("content") content : String,
        @Field("time") time : String
    ) : Observable<ResponseInsertCart>

    @POST("/api/v1/getListOrderFromName")
    @FormUrlEncoded
    fun getListOrderFromName(
        @Field("name") name : String
    ) : Observable<ModelOrderDetail>

    @POST("/api/v1/getNewsFavoriteFromIdUser")
    @FormUrlEncoded
    fun getNewsFavoriteFromIdUser(
        @Field("idUser") idUser : String
    ) : Observable<ResponsePostFavorite>
}