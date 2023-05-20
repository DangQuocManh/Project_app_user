package com.quocmanh.appproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.activity.DetailProductUserActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.ConcernProductAdapter
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.model.ProductModel
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductConcernUserFragment : Fragment(), ConcernProductAdapter.IConcernProduct {
    private lateinit var rcConcern : RecyclerView
    private lateinit var mView : View
    private lateinit var callApi: CallApi
    private lateinit var dataProduct : MutableList<Product>
    private lateinit var productModel: ProductModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_product_concern_user, container, false)
        init(mView)
        getProductConcern()
        return mView
    }
    private fun getProductConcern(){
        callApi.getAllProduct()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                productModel = it
                for(i in 0..productModel.data!!.size - 1){
                    dataProduct.add(
                        Product(
                        productModel.data!![i].id,
                        productModel.data!![i].productCode,
                        productModel.data!![i].brandCode,
                        productModel.data!![i].nameProduct,
                        productModel.data!![i].image,
                        productModel.data!![i].price,
                        productModel.data!![i].productParameter,
                        productModel.data!![i].description
                    )
                    )
                }
                rcConcern.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rcConcern.adapter = ConcernProductAdapter(this)
            },{

            })
    }
    private fun init(view : View){
        rcConcern = view.findViewById(R.id.rc_product_concern_user)
        callApi = RetrofitFactor.createRetrofit()
        dataProduct = mutableListOf()
        productModel = ProductModel()
    }

    override fun getCountItemConcern(): Int {
        return dataProduct.size
    }

    override fun getDataItemConcern(position: Int): Product {
        return dataProduct[position]
    }

    override fun onClickItemConcern(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductUserActivity::class.java)
        startActivity(intent)
    }


}