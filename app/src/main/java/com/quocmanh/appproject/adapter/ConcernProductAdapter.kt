package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class ConcernProductAdapter : RecyclerView.Adapter<ConcernProductAdapter.ConcernProductViewHolder> {
    private val inter : IConcernProduct

    constructor(inter: IConcernProduct) : super() {
        this.inter = inter
    }

    class ConcernProductViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    interface IConcernProduct {
        fun getCountItemConcern() : Int
        fun getDataItemConcern(position : Int) : Product
        fun onClickItemConcern(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcernProductViewHolder {
        return ConcernProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ConcernProductViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataItemConcern(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataItemConcern(position).price!!.toLong()).toString() + " Đ")
//        holder.binding.txtPriceProduct.text = inter.getDataItemConcern(position).price
        Glide.with(holder.itemView)
            .load(inter.getDataItemConcern(position).image)
            .into(holder.binding.imvImageProduct)
        holder.itemView.setOnClickListener {
            inter.onClickItemConcern(position)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountItemConcern()
    }
}


