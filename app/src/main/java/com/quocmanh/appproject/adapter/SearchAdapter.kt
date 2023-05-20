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

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.CourseViewHolder> {
    private var inter : ISearchData
    private var course : ArrayList<Product>
    constructor(inter : ISearchData, course : ArrayList<Product>) : super() {
        this.inter = inter
        this.course = course
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.CourseViewHolder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_search,
            parent, false
        )

        // at last we are returning our view holder
        // class with our item View File.
        return CourseViewHolder(itemView)
    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<Product>) {
        // below line is to add our filtered
        // list in our course array list.
        course = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchAdapter.CourseViewHolder, position: Int) {
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
//        fun getItemSearch() : Int
//        fun getDataListSearch() : ArrayList<Product>
//        fun getDataItemSearch(position: Int) : Product
        fun onClickItemSearch(position: Int)
    }
}