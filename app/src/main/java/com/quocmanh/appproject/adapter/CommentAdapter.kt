package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemCommentBinding
import com.quocmanh.appproject.model.Comments

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private val inter : ICommentData

    constructor(inter: ICommentData) : super() {
        this.inter = inter
    }
    class CommentViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    interface ICommentData{
        fun getCountLaptop() : Int
        fun getDataLaptop(position : Int) : Comments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.CommentViewHolder {
        return CommentAdapter.CommentViewHolder(
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        holder.binding.txtUsername.text = inter.getDataLaptop(position).userName
        holder.binding.txtTimeSend.text = inter.getDataLaptop(position).timeComment
        holder.binding.txtComment.text = inter.getDataLaptop(position).comment
        Glide.with(holder.itemView)
            .load(inter.getDataLaptop(position).avatar)
            .into(holder.binding.civAvatar)

    }

    override fun getItemCount(): Int {
        return inter.getCountLaptop()
    }
}