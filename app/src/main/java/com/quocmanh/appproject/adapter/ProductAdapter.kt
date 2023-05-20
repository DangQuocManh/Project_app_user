package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.databinding.ItemProductBinding
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private val inter : IProductData

    constructor(inter: IProductData) : super() {
        this.inter = inter
    }

    class ProductViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = inter.getDataProduct(position).nameProduct
        var decimalFormat = DecimalFormat("###,###,###")
//        holder.priceProduct.setText("Giá "+ decimalFormat.format(itemNewProduct.price.toDouble())+"Đ")
        holder.binding.txtPriceProduct.setText("Giá " + decimalFormat.format(inter.getDataProduct(position).price!!.toLong()).toString() + " Đ")
        Glide.with(holder.itemView.context)
            .load(inter.getDataProduct(position).image)
            .into(holder.binding.imvImageProduct)
        holder.binding.root.setOnClickListener {
            inter.onClickItem(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return inter.getCountProduct()
    }

    interface IProductData{
        fun getCountProduct() : Int
        fun getDataProduct(position: Int) : Product
        fun onClickItem(position: Int)
    }
}