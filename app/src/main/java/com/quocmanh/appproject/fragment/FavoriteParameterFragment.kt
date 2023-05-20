package com.quocmanh.appproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quocmanh.appproject.activity.DetailFavoriteActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.Product

class FavoriteParameterFragment : Fragment() {
    private lateinit var txtParameter : TextView
    private lateinit var mView : View
    private lateinit var detailFavoriteActivity: DetailFavoriteActivity
    private lateinit var dataProduct : MutableList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_parameter, container, false)
        init(mView)
        detailFavoriteActivity = activity as DetailFavoriteActivity
        dataProduct = detailFavoriteActivity.dataProduct
        txtParameter.text = dataProduct[0].productParameter
        return mView
    }

    private fun init(view : View) {
        txtParameter = view.findViewById(R.id.txt_parameter_favorite)
        dataProduct = mutableListOf()
    }

}