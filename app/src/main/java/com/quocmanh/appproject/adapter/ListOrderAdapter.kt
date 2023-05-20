package com.quocmanh.appproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.R
import com.quocmanh.appproject.databinding.ItemListOrderBinding
import com.quocmanh.appproject.databinding.ItemProductFavoriteBinding
import com.quocmanh.appproject.model.OrderDetail
import com.quocmanh.appproject.model.ProductFavorite
import java.text.DecimalFormat

class ListOrderAdapter : RecyclerView.Adapter<ListOrderAdapter.ListOrderViewHolder> {
    private val inter: IDataFavorite

    constructor(inter: IDataFavorite) : super() {
        this.inter = inter
    }

    class ListOrderViewHolder(val binding: ItemListOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface IDataFavorite {
        fun getItemCountOrder(): Int
        fun getDataOrder(position: Int): OrderDetail
        fun onClickItemOrder(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOrderViewHolder {
        return ListOrderViewHolder(
            ItemListOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ListOrderViewHolder, position: Int) {
        holder.binding.txtNameListOrder.text = inter.getDataOrder(position).nameUser
        holder.binding.txtEmailListOrder.text = inter.getDataOrder(position).email
        holder.binding.txtDetailListOrder.text = inter.getDataOrder(position).detail
        val decimalFormat = DecimalFormat("###,###,###")
        val money = holder.binding.txtTotalMoneyListOrder.text
        holder.binding.txtTotalMoneyListOrder.text =
            decimalFormat.format(inter.getDataOrder(position).price!!.toLong())
                .toString() + " Đ"
        holder.itemView.setOnClickListener {
            inter.onClickItemOrder(position)
        }
        if (inter.getDataOrder(position).status.equals("accept")) {
            holder.binding.txtStatusListOrder.text = "Chấp nhận"
            holder.binding.txtStatusListOrder.setTextColor(R.color.green)
        } else if (inter.getDataOrder(position).status.equals("reject")) {
            holder.binding.txtStatusListOrder.text = "Từ chối"
            holder.binding.txtStatusListOrder.setTextColor(R.color.red)
        } else if (inter.getDataOrder(position).status.equals("pending")) {
            holder.binding.txtStatusListOrder.text = "Đang chờ"
            holder.binding.txtStatusListOrder.setTextColor(R.color.yellow)
        }
    }

    override fun getItemCount(): Int {
        return inter.getItemCountOrder()
    }
}