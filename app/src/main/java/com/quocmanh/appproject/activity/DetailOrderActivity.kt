package com.quocmanh.appproject.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.appproject.R
import java.text.DecimalFormat

class DetailOrderActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imvBack : ImageView
    private lateinit var txtName : TextView
    private lateinit var txtEmail : TextView
    private lateinit var txtPhone : TextView
    private lateinit var txtDetail : TextView
    private lateinit var txtMoney : TextView
    private lateinit var txtTime : TextView
    private lateinit var txtAddress : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)
        init()
        getData()
    }

    fun getData() {
        val intent = intent
        val bundle = intent.extras
        val id : Int = bundle!!.getInt("id")
        val name : String = bundle.getString("name_user").toString()
        val email : String = bundle.getString("email").toString()
        val phone : String = bundle.getString("phone_user").toString()
        val detail : String = bundle.getString("detail").toString()
        val address : String = bundle.getString("address").toString()
        val dateCreate : String = bundle.getString("date_create").toString()
        val status : String = bundle.getString("status").toString()
        val price : String = bundle.getString("price").toString()
        txtName.text = name
        txtEmail.text = email
        txtPhone.text = phone
        txtDetail.text = detail
        val decimalFormat = DecimalFormat("###,###,###")
        txtMoney.text = decimalFormat.format(price.toFloat()).toString() + "Đ"
        txtAddress.text = address
        txtTime.text = dateCreate
    }

    fun init() {
        imvBack = findViewById(R.id.imv_back_detail_order_acti)
        txtName = findViewById(R.id.txt_name_order_detail)
        txtEmail = findViewById(R.id.txt_email_order_detail)
        txtPhone = findViewById(R.id.txt_phone_order_detail)
        txtDetail = findViewById(R.id.txt_detail_order_detail)
        txtMoney = findViewById(R.id.txt_total_price_detail)
        txtTime = findViewById(R.id.txt_time_order_detail)
        txtAddress = findViewById(R.id.txt_address_order_detail)
        imvBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imv_back_detail_order_acti -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}