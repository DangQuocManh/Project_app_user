package com.quocmanh.appproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quocmanh.appproject.activity.DetailProductUserActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.model.Product

class DescriptionUserFragment : Fragment() {
    private lateinit var txtDescription : TextView
    private lateinit var detailProductActivity: DetailProductUserActivity
    private lateinit var dataProduct : Product
    private lateinit var mView : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_description_user, container, false)
        detailProductActivity = activity as DetailProductUserActivity
        dataProduct = detailProductActivity.listProduct
        init(mView)
        txtDescription.text = dataProduct.description
        return mView
    }
    private fun init(view : View){
        txtDescription = view.findViewById(R.id.txt_description_user)
    }
}