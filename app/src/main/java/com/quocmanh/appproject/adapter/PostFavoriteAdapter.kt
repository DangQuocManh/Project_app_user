package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemNewsBinding
import com.quocmanh.appproject.databinding.ItemPostFavoriteBinding
import com.quocmanh.appproject.model.Post
import com.quocmanh.appproject.model.PostFavorite

class PostFavoriteAdapter : RecyclerView.Adapter<PostFavoriteAdapter.PostFavoriteHolder> {
    private val inter: INewsData

    constructor(inter: INewsData) : super() {
        this.inter = inter
    }

    class PostFavoriteHolder(val binding: ItemPostFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface INewsData {
        fun getCountNews(): Int
        fun getDataNews(position: Int): PostFavorite
        fun onClickItemNews(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostFavoriteHolder {
        return PostFavoriteHolder(
            ItemPostFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostFavoriteHolder, position: Int) {
        holder.binding.txtTitleNewsFavorite.text = inter.getDataNews(position).title.toString()
        holder.binding.txtContentItemFavorite.text = inter.getDataNews(position).content.toString()
        Glide.with(holder.itemView)
            .load(inter.getDataNews(position).image)
            .into(holder.binding.imvImageNews1Favorite)
        holder.binding.root.setOnClickListener {
            inter.onClickItemNews(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return inter.getCountNews()
    }
}