package com.quocmanh.appproject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.SearchAdvencedAdapter
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchAdvancedActivity : AppCompatActivity(), SearchAdvencedAdapter.ISearchData {
    private lateinit var imgBackSearch : ImageButton
    private lateinit var rcProduct : RecyclerView
    private lateinit var callApi: CallApi
    private lateinit var listData : MutableList<Product>
    lateinit var strType : String
    lateinit var strBrand : String
    lateinit var strPrice : String
    lateinit var searchAdvencedAdapter: SearchAdvencedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_advanced)
        init()
        getData()
        imgBackSearch.setOnClickListener{
            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun init() {
        imgBackSearch = findViewById(R.id.imb_back_search_again)
        rcProduct = findViewById(R.id.rc_search_advanced)
        callApi = RetrofitFactor.createRetrofit()
        listData = mutableListOf()
    }
    private fun getData() {
        val intent = intent
        val bundle = intent.extras
        strType = bundle!!.getString("type").toString()
        strBrand = bundle.getString("brand").toString()
        strPrice = bundle.getString("price").toString()
        callApi.getDataSearchAdvanced(
                    strType, strBrand, strPrice
                ).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val test = it.message
                        Log.d("search advanced: ", test.toString())
                        listData = it.data!!
                        if(listData == null || listData?.size == 0) {
                            Toast.makeText(this, "Không có dữ liệu!", Toast.LENGTH_LONG).show()
                        } else {
                            searchAdvencedAdapter = SearchAdvencedAdapter(this, listData!!)
                            rcProduct.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
                            rcProduct.adapter = searchAdvencedAdapter
                            searchAdvencedAdapter.notifyDataSetChanged()
                        }

                    },{

                    })
    }

    override fun onClickItemSearch(position: Int) {
        val intent = Intent()
        intent.setClass(this, DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", listData!![position].id!!.toInt())
        bundle.putString("product_code", listData!![position].productCode)
        bundle.putString("product_name", listData!![position].nameProduct)
        bundle.putString("image", listData!![position].image)
        bundle.putString("price", listData!![position].price)
        bundle.putString("product_patameters", listData!![position].productParameter)
        bundle.putString("description", listData!![position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}