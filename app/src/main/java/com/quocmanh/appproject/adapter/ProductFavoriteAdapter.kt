package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductFavoriteBinding
import com.quocmanh.appproject.model.ProductFavorite
import java.text.DecimalFormat

class ProductFavoriteAdapter : RecyclerView.Adapter<ProductFavoriteAdapter.ProductFavoriteViewHolder> {
    private val inter : IDataFavorite

    constructor(inter: IDataFavorite) : super() {
        this.inter = inter
    }

    class ProductFavoriteViewHolder(val binding: ItemProductFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    interface IDataFavorite {
        fun getItemCountFavorite() : Int
        fun getDataFavorite(position : Int) : ProductFavorite
        fun onClickItemFavorite(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductFavoriteViewHolder {
        return ProductFavoriteViewHolder(
            ItemProductFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductFavoriteViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataFavorite(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.text = "Giá: " + decimalFormat.format(inter.getDataFavorite(position).price!!.toLong()).toString() + " Đ"
        Glide.with(holder.itemView)
            .load(inter.getDataFavorite(position).image)
            .into(holder.binding.imvImageProduct)
        holder.itemView.setOnClickListener{
            inter.onClickItemFavorite(position)
        }
    }

    override fun getItemCount(): Int {
        return inter.getItemCountFavorite()
    }
}
