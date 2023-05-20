package com.quocmanh.appproject.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemNewsBinding
import com.quocmanh.appproject.model.Post
import com.quocmanh.appproject.model.news
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.ImageClick
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private val inter : INewsData

    constructor(inter: INewsData) : super() {
        this.inter = inter
    }

    class NewsViewHolder(val binding : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
//        lateinit var imageClick : ImageClick
//
//
//        @JvmName("setImageClick1")
//        fun setImageClick(imageClick : ImageClick){
//            this.imageClick = imageClick
//        }
//
    }
    interface INewsData{
        fun getCountNews() : Int
        fun getDataNews(position : Int) : Post
        fun onClickItemNews(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.txtTitleNews.text = inter.getDataNews(position).title
        holder.binding.txtContentItem.text = inter.getDataNews(position).content
        Glide.with(holder.itemView)
            .load(inter.getDataNews(position).imagePost)
            .into(holder.binding.imvImageNews1)
        holder.binding.root.setOnClickListener {
            inter.onClickItemNews(holder.adapterPosition)
        }
        holder.binding.imvBookmark.setOnClickListener {
            Log.d("Position: ", position.toString())
            lateinit var callApi: CallApi
            lateinit var sharedPreferences: SharedPreferences
            var idUser : Int? = null
            sharedPreferences = holder.binding.root.context.getSharedPreferences("user", Context.MODE_PRIVATE)
            idUser = sharedPreferences.getString("id_user", "")!!.toInt()
            callApi = RetrofitFactor.createRetrofit()
            callApi.addPostFavorite(inter.getDataNews(position).id, idUser.toInt(), inter.getDataNews(position).imagePost.toString(),
                inter.getDataNews(position).title, inter.getDataNews(position).content, inter.getDataNews(position).timeCreate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val mess = it.message
                    if(mess.equals("ok")) {
                        Toast.makeText(holder.binding.root.context, "Thêm thành công!", Toast.LENGTH_LONG).show()
                    }
                },{

                })
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountNews()
    }
}