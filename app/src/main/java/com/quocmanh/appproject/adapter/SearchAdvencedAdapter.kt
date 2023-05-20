package com.quocmanh.appproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class SearchAdvencedAdapter : RecyclerView.Adapter<SearchAdvencedAdapter.CourseViewHolder> {
    private var inter : ISearchData
    private var course : MutableList<Product>
    constructor(inter : ISearchData, course : MutableList<Product>) : super() {
        this.inter = inter
        this.course = course
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdvencedAdapter.CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_search,
            parent, false
        )
        return CourseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // on below line we are returning
        // our size of our list
        return course.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our course name text view and our image view.
        val nameProduct: TextView = itemView.findViewById(R.id.txt_name_phone)
        val priceProduct: TextView = itemView.findViewById(R.id.txt_pice_phone)
        val imageProduct : ImageView = itemView.findViewById(R.id.imv_image)
    }
    interface ISearchData {
        fun onClickItemSearch(position: Int)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.
        holder.nameProduct.text = course.get(position).nameProduct
//        holder.priceProduct.setImageResource(courseList.get(position).courseImg)
        val decimalFormat = DecimalFormat("###,###,###")
        holder.priceProduct.text = "Giá: " + decimalFormat.format(course.get(position).price!!.toDouble()).toString() + " Đ"
        Glide.with(holder.itemView)
            .load(course.get(position).image)
            .into(holder.imageProduct)
        holder.itemView.setOnClickListener({
            inter.onClickItemSearch(position)
        })
    }
}