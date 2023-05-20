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

class ParameterUserFragment : Fragment() {
    private lateinit var txtParameter : TextView
    private lateinit var mView : View
    private lateinit var dataProduct : Product
    private lateinit var detailProductActivity: DetailProductUserActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_parameter_user, container, false)
        detailProductActivity = activity as DetailProductUserActivity
        dataProduct = detailProductActivity.listProduct
        init(mView)
        txtParameter.text = dataProduct.productParameter
        return mView
    }
    private fun init(view : View){
        txtParameter = view.findViewById(R.id.txt_parameter_product_user)
    }

}