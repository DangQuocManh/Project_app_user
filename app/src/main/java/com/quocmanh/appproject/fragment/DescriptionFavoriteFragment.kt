package com.quocmanh.appproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.DetailFavoriteActivity
import com.quocmanh.appproject.activity.DetailProductActivity
import com.quocmanh.appproject.model.Product

class DescriptionFavoriteFragment : Fragment() {
    private lateinit var txtDescription : TextView
    private lateinit var detailProductActivity: DetailFavoriteActivity
    private lateinit var dataProduct : Product
    private lateinit var mView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_description_favorite, container, false)
        detailProductActivity = activity as DetailFavoriteActivity
        dataProduct = detailProductActivity.dataProduct1[0]
        init(mView)
        txtDescription.text = dataProduct.description
        return mView
    }
    private fun init(view : View){
        txtDescription = view.findViewById(R.id.txt_description_favorite_product)
    }
}