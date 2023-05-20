package com.quocmanh.appproject.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.quocmanh.appproject.R
import com.quocmanh.appproject.fragment.DetailProductFragment
import com.quocmanh.appproject.model.*
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailProductActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnSaveProduct : Button
    private lateinit var btnAddCart : Button
    lateinit var listProduct : Product
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameProduct : String
    private lateinit var image : String
    private lateinit var price : String
    private var idProduct : Int? = null
    private lateinit var callApi: CallApi
    private lateinit var btnComment : Button
    var id : Int? = null
    var productParameter : String = ""
    var description : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
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
        btnSaveProduct = findViewById(R.id.btn_save_product)
        btnAddCart = findViewById(R.id.btn_add_cart)
        btnSaveProduct.setOnClickListener(this)
        btnAddCart.setOnClickListener(this)
        sharedPreferences = applicationContext.getSharedPreferences("user", MODE_PRIVATE)
        callApi = RetrofitFactor.createRetrofit()
        btnComment = findViewById(R.id.btn_comment)
        btnComment.setOnClickListener(this)
        listProduct = Product()
    }
    private fun getFragment(){
        val detailProductFragment = DetailProductFragment()
        val fm : FragmentManager = supportFragmentManager
        val fg = fm.beginTransaction()
        fg.add(R.id.fr_detail_product, detailProductFragment)
        fg.addToBackStack(null)
        fg.commit()
    }
    private fun addProductToCart(){
        val idUser = sharedPreferences.getString("id_user", "")!!.toString()
        val productId = idProduct
        val productName = nameProduct
        val imageProduct = image
        val priceProduct = price
        var countProduct : Int = 1
        val obj = RequestInsertCart(productName, imageProduct, priceProduct, countProduct!!.toString(), idUser!!.toString(), productId!!.toString())

        callApi.insertCart(productName, imageProduct,priceProduct, countProduct.toInt(), idUser.toInt(), productId.toInt())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val message = it.message
                if(message == "added") {
                    Toast.makeText(applicationContext, "Sản phẩm đã có trong giỏ hàng!", Toast.LENGTH_LONG).show()
                } else if(message == "ok"){
                    Toast.makeText(applicationContext, "Đã thêm giỏ hàng!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Chưa thêm được vào giỏ hàng!", Toast.LENGTH_LONG).show()
                }
            },{
                Log.d("message: ", "error")
            })
    }
    private fun saveProduct(){
        val idUser = sharedPreferences.getString("id_user", "")!!.toString()
        val productId = idProduct
        val productName = nameProduct
        val imageProduct = image
        val priceProduct = price
        callApi.insertFavorite(productName, imageProduct, priceProduct, idUser.toInt(), productId!!.toInt())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mess = it.message
                if(mess == "ok"){
                    Toast.makeText(applicationContext, "Yêu thích!", Toast.LENGTH_LONG).show()
                }
            },{

            })
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_save_product -> {
                saveProduct()
            }
            R.id.btn_add_cart -> {
                addProductToCart()
            }
            R.id.btn_comment -> {
                val intent = Intent()
                intent.setClass(this, CommentsActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("id_product", id!!.toInt())
                bundle.putString("product_name", nameProduct)
                bundle.putString("image", image)
                bundle.putString("price", price)
                bundle.putString("parameter", productParameter)
                bundle.putString("description", description)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}