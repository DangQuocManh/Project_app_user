package com.quocmanh.appproject.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.CartAdapter
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class CartActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imbBack : ImageButton
    private lateinit var callApi: CallApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rcCart : RecyclerView
    private lateinit var txtTotal : TextView
    private lateinit var totalMoney : String
    private var isRunning : Boolean = false
    private lateinit var btnCheckout : Button
//    lateinit var totalMoneyTask: TotalMoneyTask

    val thread = Thread() {
            run {
                while (true) {
                    if (isRunning == false) {
                        val idUser : Int = sharedPreferences.getString("id_user", "")!!.toInt()
                        callApi.getDataCart(idUser)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                val mess = it.message
                                if (mess == "ok") {
                                    val dataList = it.dataCart
                                    if(dataList.size > 0) {
                                        var total: Int = 0
                                        for (i in 0..dataList.size - 1) {
                                            total += dataList[i].price.toInt()
                                        }
                                        totalMoney = total.toString()
                                        val decimalFormat = DecimalFormat("###,###,###")
                                        txtTotal.text = decimalFormat.format(totalMoney.toDouble())
                                            .toString() + " Đ"
                                        Log.d("Cart: ", "not null")
                                    } else if(dataList.size == 0 || dataList.isEmpty()) {
                                        txtTotal.text = "0 Đ"
                                    }
                                } else if (mess == "not_cart") {
                                    Log.d("Cart: ", "null")
                                    txtTotal.text = "0 Đ"
                                }
                            }, {

                            })
                    } else if(isRunning == true) {
                        return@Thread
                    }
                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
        getDataCart()
//        totalMoneyTask.execute()
        thread.start()
    }

    private fun getDataCart(){
        val idUser : Int = sharedPreferences.getString("id_user", "")!!.toInt()
        callApi.getDataCart(idUser)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mess = it.message
                if(mess == "ok"){
                    val dataList = it.dataCart
                    val t = dataList.size
                    val t1 = dataList[0].name_product
                    val t2 = dataList[0].count_product
                    Log.d("Data list: ", t.toString())
                    val adapter = CartAdapter(this@CartActivity, dataList)
                    rcCart.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
                    rcCart.adapter = adapter
                } else if(mess == "not_cart") {
                    Toast.makeText(this, "Cart null!", Toast.LENGTH_LONG).show()
                }
            },{

            })
    }

    private fun init(){
        imbBack = findViewById(R.id.imb_back)
        imbBack.setOnClickListener(this)
        callApi = RetrofitFactor.createRetrofit()
        sharedPreferences = applicationContext.getSharedPreferences("user", MODE_PRIVATE)
        rcCart = findViewById(R.id.rc_cart)
        txtTotal = findViewById(R.id.txt_total)
        totalMoney = ""
        btnCheckout = findViewById(R.id.btn_checkout)
        btnCheckout.setOnClickListener(this)
//        totalMoneyTask = TotalMoneyTask(this@CartActivity, txtTotal)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imb_back -> {
                isRunning = true
                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_checkout -> {
                isRunning = true
                val intent = Intent()
                intent.setClass(this, PayActivity::class.java)
                startActivity(intent)
            }
        }
    }
}