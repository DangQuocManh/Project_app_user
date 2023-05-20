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

class FavoriteDescriptionFragment : Fragment() {
    private lateinit var txtDesFavorite : TextView
    private lateinit var mView : View
    private lateinit var detailFavoriteActivity: DetailFavoriteActivity
    private lateinit var dataProduct : MutableList<Product>
    lateinit var data : Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_description, container, false)
        init(mView)
        detailFavoriteActivity = activity as DetailFavoriteActivity
        data = detailFavoriteActivity.data1
        txtDesFavorite.text = data.description
        return mView
    }
    private fun init(view : View) {
        txtDesFavorite = view.findViewById(R.id.txt_description_favorite)
        dataProduct = mutableListOf()
        data = Product()
    }

}