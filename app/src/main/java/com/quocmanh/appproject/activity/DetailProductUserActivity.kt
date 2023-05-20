package com.quocmanh.appproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.quocmanh.appproject.R
import com.quocmanh.appproject.fragment.DetailProductUserFragment
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor

class DetailProductUserActivity : AppCompatActivity() {
    lateinit var listProduct : Product
    private lateinit var nameProduct : String
    private lateinit var image : String
    private lateinit var price : String
    private var idProduct : Int? = null
    private lateinit var callApi: CallApi
    var id : Int? = null
    var productParameter : String = ""
    var description : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product_user)
        init()
        getFragment()
        getData()
    }
    private fun getData() {
        val intent = intent
        val bundle = intent.extras
        if(bundle != null) {
            id  = bundle.getInt("id")
            val productCode : String = bundle.getString("product_code").toString()
            idProduct = bundle.getInt("id").toInt()
            nameProduct = bundle.getString("product_name").toString()
            val brandCode = bundle.getString("brand_code").toString()
            image = bundle.getString("image").toString()
            price = bundle.getString("price").toString()
            productParameter = bundle.getString("product_patameters").toString()
            description = bundle.getString("description").toString()
            listProduct = Product(id, productCode, brandCode, nameProduct, image, price, productParameter, description)
        }
    }
    private fun init(){
        callApi = RetrofitFactor.createRetrofit()
    }
    private fun getFragment(){
        val detailProductFragment = DetailProductUserFragment()
        val fm : FragmentManager = supportFragmentManager
        val fg = fm.beginTransaction()
        fg.add(R.id.fr_detail_product_user, detailProductFragment)
        fg.addToBackStack(null)
        fg.commit()
    }
}