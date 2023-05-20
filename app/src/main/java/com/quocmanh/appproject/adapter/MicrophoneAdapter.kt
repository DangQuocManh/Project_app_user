package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class MicrophoneAdapter : RecyclerView.Adapter<MicrophoneAdapter.MicrophoneViewHolder> {
    private val inter : IMicroData

    constructor(inter: IMicroData) : super() {
        this.inter = inter
    }

    class MicrophoneViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    interface IMicroData {
        fun getCountMicro() : Int
        fun getDataMicro(position : Int) : Product
        fun onClickItemMicro(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MicrophoneViewHolder {
        return MicrophoneViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MicrophoneViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataMicro(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataMicro(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView)
            .load(inter.getDataMicro(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItemMicro(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountMicro()
    }

}