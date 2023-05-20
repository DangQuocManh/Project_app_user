package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class HeadphoneAdapter : RecyclerView.Adapter<HeadphoneAdapter.HeadphoneViewHolder> {
    private val inter : IHeadphoneData

    constructor(inter: IHeadphoneData) : super() {
        this.inter = inter
    }

    class HeadphoneViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    interface IHeadphoneData{
        fun getCountHeadphone() : Int
        fun getDataHeadphone(position : Int) : Product
        fun onClickItemHeadphone(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadphoneViewHolder {
        return HeadphoneViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HeadphoneViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataHeadphone(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataHeadphone(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView)
            .load(inter.getDataHeadphone(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItemHeadphone(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountHeadphone()
    }

}