package com.quocmanh.appproject.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.activity.DetailProductUserActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.adapter.SearchAdvencedAdapter
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoriesProductUserFragment : Fragment(), SearchAdvencedAdapter.ISearchData,
    View.OnClickListener {
    private lateinit var imvBack : ImageView
    private lateinit var txtType : TextView
    private lateinit var rcvType : RecyclerView
    private lateinit var mView : View
    private lateinit var sharedPreferences: SharedPreferences
    var typeCategories : String = ""
    private lateinit var callApi: CallApi
    private lateinit var listData : MutableList<Product>
    lateinit var searchAdvencedAdapter: SearchAdvencedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_categories_product_user, container, false)
        init(mView)
        getDataType()
        return mView
    }
    private fun getDataType() {
        callApi.getFromProductCode(typeCategories)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val test = it.message
                Log.d("search advanced: ", test.toString())
                listData = it.data!!
                if(listData == null || listData?.size == 0) {
                    Toast.makeText(requireContext(), "Không có dữ liệu!", Toast.LENGTH_LONG).show()
                } else {
                    searchAdvencedAdapter = SearchAdvencedAdapter(this, listData!!)
                    rcvType.layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
                    rcvType.adapter = searchAdvencedAdapter
                    searchAdvencedAdapter.notifyDataSetChanged()
                }

            },{

            })
    }
    private fun init(view: View) {
        imvBack = view.findViewById(R.id.imv_back_type_user)
        txtType = view.findViewById(R.id.txt_type_user)
        rcvType = view.findViewById(R.id.rcv_categories_user)
        imvBack.setOnClickListener(this)
        sharedPreferences = requireContext().getSharedPreferences("type_categories",
            Context.MODE_PRIVATE
        )
        typeCategories = sharedPreferences.getString("type", "").toString()
        callApi = RetrofitFactor.createRetrofit()
        listData = mutableListOf()
        if(typeCategories == "LT1") {
            txtType.text = "Laptop"
        } else if(typeCategories == "PH1") {
            txtType.setText("Điện thoại")
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imv_back_type -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onClickItemSearch(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductUserActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", listData!![position].id!!.toInt())
        bundle.putString("product_code", listData!![position].productCode)
        bundle.putString("product_name", listData!![position].nameProduct)
        bundle.putString("image", listData!![position].image)
        bundle.putString("price", listData!![position].price)
        bundle.putString("product_patameters", listData!![position].productParameter)
        bundle.putString("description", listData!![position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}