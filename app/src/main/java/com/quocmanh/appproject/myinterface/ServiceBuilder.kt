package com.quocmanh.appproject.myinterface
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quocmanh.appproject.R

import com.quocmanh.appproject.Util.Path
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    val gson : Gson
        get() = GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create()

    private val retrofit1 = Retrofit.Builder()
        .baseUrl(Path.url) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun<T> buildService1(service: Class<T>): T{
        return retrofit1.create(service)
    }
}