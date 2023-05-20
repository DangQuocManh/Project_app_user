package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class ScreenAdapter : RecyclerView.Adapter<ScreenAdapter.ScreenViewHolder> {
    private val inter : IScreenData

    constructor(inter: IScreenData) : super() {
        this.inter = inter
    }

    class ScreenViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    interface IScreenData{
        fun getCountScreen() : Int
        fun getDataScreen(position : Int) : Product
        fun onClickItemScreen(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenViewHolder {
        return ScreenViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ScreenViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataScreen(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataScreen(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView)
            .load(inter.getDataScreen(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItemScreen(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountScreen()
    }
}