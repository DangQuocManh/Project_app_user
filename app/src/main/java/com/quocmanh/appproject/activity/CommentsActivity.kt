package com.quocmanh.appproject.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.CartAdapter
import com.quocmanh.appproject.adapter.CommentAdapter
import com.quocmanh.appproject.model.Comments
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CommentsActivity : AppCompatActivity(), View.OnClickListener, CommentAdapter.ICommentData {
    private lateinit var imbBack : ImageButton
    private lateinit var imvImage : ImageView
    private lateinit var txtNameProduct : TextView
    private lateinit var txtPriceProduct : TextView
    private lateinit var edtComment : EditText
    private lateinit var imvSend : ImageView
    private lateinit var rcComments : RecyclerView
    var idPro : Int? = null
    var nameProduct : String = ""
    var imageProduct : String = ""
    var priceProduct : String = ""
    var parameterProduct : String = ""
    var descriptionProduct : String = ""
    var idUser : Int? = null
    var nameUser : String = ""
    private lateinit var callApi: CallApi
    private lateinit var sharedPreferences: SharedPreferences
    private var isRunning : Boolean = false
    private lateinit var list : MutableList<Comments>
    val thread = Thread() {
        run {
            while (true) {
                if (isRunning == false) {
                    callApi.getComments(idPro!!.toInt())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val mess = it.message
                            if (mess == "ok") {
                                val dataList = it.data
                                list = dataList as MutableList<Comments>
                                if(dataList?.size == 0 || dataList!!.isEmpty()) {
//                                    Toast.makeText(this, "Comment null!", Toast.LENGTH_LONG).show()
                                } else {
                                    val adapter = CommentAdapter(this)
                                    rcComments.layoutManager = LinearLayoutManager(this@CommentsActivity, LinearLayoutManager.VERTICAL, false)
                                    rcComments.adapter = adapter
                                }
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
        setContentView(R.layout.activity_comments)
        init()
        getData()
        thread.start()
        imvSend.setOnClickListener {
            addComment()
        }
    }

    private fun init() {
        imbBack = findViewById(R.id.imb_back_comment)
        imvImage = findViewById(R.id.imv_image_comment)
        txtNameProduct = findViewById(R.id.txt_name_comment)
        txtPriceProduct = findViewById(R.id.txt_price_comment)
        edtComment = findViewById(R.id.edt_send_message)
        imvSend = findViewById(R.id.imv_send_comment)
        imbBack.setOnClickListener(this)
        sharedPreferences = applicationContext.getSharedPreferences("user", MODE_PRIVATE)
        idUser = sharedPreferences.getString("id_user", "")!!.toInt()
        nameUser = sharedPreferences.getString("name_user", "")!!.toString()
        callApi = RetrofitFactor.createRetrofit()
        rcComments = findViewById(R.id.rc_comments)
        list = mutableListOf()
    }

    private fun getData() {
        val intent = intent
        val bundle = intent.extras
        if(bundle != null) {
            idPro = bundle.getInt("id_product")
            nameProduct = bundle.getString("product_name").toString()
            imageProduct = bundle.getString("image").toString()
            priceProduct = bundle.getString("price").toString()
            parameterProduct = bundle.getString("parameter").toString()
            descriptionProduct = bundle.getString("description").toString()
            txtNameProduct.text = nameProduct.toString()
            val decimalFormat = DecimalFormat("###,###,###")
            txtPriceProduct.text = "Giá: " + decimalFormat.format(priceProduct.toLong()).toString() + " Đ"
            Glide.with(this)
                .load(imageProduct)
                .into(imvImage)
        }
    }
    private fun addComment() {
        val comment : String = edtComment.text.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        callApi.addComments(idUser!!.toInt(), idPro!!.toInt(), comment, currentDate.toString(), nameUser, "")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mes = it.message
                edtComment.setText(null)
            },{
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imb_back_comment -> {
                isRunning = true
                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getCountLaptop(): Int {
        return list.size
    }

    override fun getDataLaptop(position: Int): Comments {
        return list[position]
    }
}