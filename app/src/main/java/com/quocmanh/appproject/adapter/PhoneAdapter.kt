package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class PhoneAdapter : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private val inter : IPhoneData

    constructor(inter: IPhoneData) : super() {
        this.inter = inter
    }

    class PhoneViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        return PhoneViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataPhone(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataPhone(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView)
            .load(inter.getDataPhone(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItemPhone(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountPhone()
    }

    interface IPhoneData{
        fun getCountPhone() : Int
        fun getDataPhone(position: Int) : Product
        fun onClickItemPhone(position: Int)
    }

}