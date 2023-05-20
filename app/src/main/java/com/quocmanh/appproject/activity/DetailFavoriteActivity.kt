package com.quocmanh.appproject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.fragment.DetailFavoriteFragment
import com.quocmanh.appproject.model.*
import com.quocmanh.appproject.myinterface.ApiInterface
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import com.quocmanh.appproject.myinterface.ServiceBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFavoriteActivity : AppCompatActivity() {
    private lateinit var btnAddCart: Button
    lateinit var dataProduct: MutableList<Product>
    lateinit var dataProduct1: MutableList<Product>
    lateinit var data: Product
    lateinit var data1: Product
    private lateinit var callApi: CallApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_favorite)
        init()
        getFragment()
        getData()
    }

    private fun init() {
        btnAddCart = findViewById(R.id.btn_add_cart)
        callApi = RetrofitFactor.createRetrofit()
        dataProduct = mutableListOf()
        dataProduct1 = mutableListOf()
        data = Product()
        data1 = Product()
    }

    private fun getFragment() {
        val detailFavoriteFragment = DetailFavoriteFragment()
        val fg = supportFragmentManager
        val fm = fg.beginTransaction()
        fm.add(R.id.fr_detail_favorite, detailFavoriteFragment)
        fm.addToBackStack(null)
        fm.commit()
    }

    private fun getData() {
        val intent = intent
        val bundle = intent.extras
        val idProduct: Int = bundle!!.getInt("id_product")
        val t1 = idProduct
        callApi.getDataProFromId(t1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mess = it.message
                dataProduct1 = it.data!!
                data1 = dataProduct1[0]
            },{

            })
    }
}