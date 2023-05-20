package com.quocmanh.appproject.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.quocmanh.appproject.R
import com.quocmanh.appproject.fragment.NewsFragment
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailNewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imvBack : ImageView
    private lateinit var txtTitle : TextView
    private lateinit var civAvatar : CircleImageView
    private lateinit var txtName : TextView
    private lateinit var txtTimeCreate : TextView
    private lateinit var imvBookmark : ImageView
    private lateinit var txtContent : TextView
    private lateinit var imvImagePost : ImageView
    private lateinit var callApi: CallApi
    private var idPost : Int? = null
    var imagePost : String = ""
    var title : String = ""
    var content : String = ""
    var timeCreate : String = ""
    var idUser : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        init()
        getDataPostDetail()
    }
    private fun init() {
        imvBack = findViewById(R.id.imv_back_news_detail)
        txtTitle = findViewById(R.id.txt_title_detail_news)
        civAvatar = findViewById(R.id.civ_avatar_author)
        txtName = findViewById(R.id.txt_name_author)
        txtTimeCreate = findViewById(R.id.txt_time_create_news)
        imvBookmark = findViewById(R.id.imv_bookmark_detail_news)
        txtContent = findViewById(R.id.txt_content_news_detail)
        imvBack.setOnClickListener(this)
        imvBookmark.setOnClickListener(this)
        callApi = RetrofitFactor.createRetrofit()
        imvImagePost = findViewById(R.id.imv_image_news_detail)
    }

    fun getDataPostDetail() {
        val intent = intent
        val bundle = intent.extras
        idPost   = bundle!!.getInt("id")
        imagePost   = bundle.getString("image_post").toString()
        title   = bundle.getString("title").toString()
        content  = bundle.getString("content").toString()
         timeCreate = bundle.getString("time_create").toString()
         idUser  = bundle!!.getInt("id_user")
        Glide.with(this)
            .load(imagePost)
            .into(imvImagePost)
        txtTitle.text = title.toString()
        txtContent.text = content.toString()
        txtTimeCreate.text = timeCreate.toString()

        callApi.getDataUser(
            idUser!!
        ).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mess : String = it.message
                if (mess.equals("ok")) {
                    val user = it.dataLogin
                    val imgAvatar = user?.get(0)?.avatar.toString()
                    val name = user!![0].userName.toString()
                    Glide.with(this)
                        .load(imgAvatar)
                        .into(civAvatar)
                    txtName.text = name.toString()
                }
            },{

            })

    }

    @SuppressLint("ResourceType")
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imv_back_news_detail -> {
                val intent = Intent()
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.imv_bookmark_detail_news -> {
                lateinit var sharedPreferences: SharedPreferences
//                var idUser : Int? = null
//                sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE)
//                idUser = sharedPreferences.getString("id_user", "")!!.toInt()
                callApi.addPostFavorite(idPost!!.toInt(), idUser!!.toInt(), imagePost, title, content, timeCreate)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val mess = it.message
                        if(mess.equals("ok")) {
                            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_LONG).show()
                        }
                    },{

                    })
            }
        }
    }
}