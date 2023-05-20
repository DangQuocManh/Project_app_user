package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class TabletAdapter : RecyclerView.Adapter<TabletAdapter.TabletViewHolder> {
    private val inter : ITabletData

    constructor(inter: ITabletData) : super() {
        this.inter = inter
    }

    class TabletViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    interface ITabletData{
        fun getCountTablet() : Int
        fun getDataTablet(position : Int) : Product
        fun onClickItemTablet(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabletViewHolder {
        return TabletViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TabletViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataTablet(position).nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataTablet(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView)
            .load(inter.getDataTablet(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItemTablet(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountTablet()
    }
}