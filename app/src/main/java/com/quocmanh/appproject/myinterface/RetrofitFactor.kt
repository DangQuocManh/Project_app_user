package com.quocmanh.appproject.myinterface

import com.quocmanh.appproject.Util.Path
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactor {
    fun createRetrofit() : CallApi{
        return Retrofit.Builder().baseUrl(Path.url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(CallApi::class.java)
    }
}