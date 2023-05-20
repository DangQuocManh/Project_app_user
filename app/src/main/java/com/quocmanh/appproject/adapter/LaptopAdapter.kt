package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class LaptopAdapter : RecyclerView.Adapter<LaptopAdapter.LaptopViewHolder> {
    private val inter : ILaptopData

    constructor(inter: ILaptopData) : super() {
        this.inter = inter
    }

    class LaptopViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    interface ILaptopData{
        fun getCountLaptop() : Int
        fun getDataLaptop(position : Int) : Product
        fun onClickItemLaptop(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaptopViewHolder {
        return LaptopViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LaptopViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataLaptop(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataLaptop(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView)
            .load(inter.getDataLaptop(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItemLaptop(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountLaptop()
    }
}