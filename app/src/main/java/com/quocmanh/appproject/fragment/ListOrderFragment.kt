package com.quocmanh.appproject.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.DetailOrderActivity
import com.quocmanh.appproject.adapter.ListOrderAdapter
import com.quocmanh.appproject.model.OrderDetail
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ListOrderFragment : Fragment(), View.OnClickListener, ListOrderAdapter.IDataFavorite {
    private lateinit var imvBack : ImageView
    private lateinit var rcvListOrder : RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mView : View
    private var name : String = ""
    private lateinit var callApi: CallApi
    private lateinit var listOrder : MutableList<OrderDetail>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_list_order, container, false)
        init(mView)
        getData()
        return mView
    }

    fun init(view: View) {
        imvBack = view.findViewById(R.id.imv_back_list_order)
        rcvListOrder = view.findViewById(R.id.rcv_list_order)
        imvBack.setOnClickListener(this)
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        name = sharedPreferences.getString("name_user", "").toString()
        callApi = RetrofitFactor.createRetrofit()
        listOrder = mutableListOf()
    }

    fun getData() {
        callApi.getListOrderFromName(name)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mess : String = it.message
                if(mess.equals("ok")) {
                    listOrder = it.dataList
                    val listOrderAdapter = ListOrderAdapter(this)
                    rcvListOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    rcvListOrder.adapter = listOrderAdapter
                }
            },{

            })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imv_back_list_order -> {
                val accountFragment = AccountFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, accountFragment)
                fg.commit()
            }
        }
    }

    override fun getItemCountOrder(): Int {
        return listOrder.size
    }

    override fun getDataOrder(position: Int): OrderDetail {
        return listOrder[position]
    }

    override fun onClickItemOrder(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailOrderActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", listOrder[position].id)
        bundle.putString("name_user", listOrder[position].nameUser)
        bundle.putString("email", listOrder[position].email)
        bundle.putString("phone_user", listOrder[position].phone)
        bundle.putString("detail", listOrder[position].detail)
        bundle.putString("address", listOrder[position].address)
        bundle.putString("date_create", listOrder[position].dateCreate)
        bundle.putString("status", listOrder[position].status)
        bundle.putString("price", listOrder[position].price)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}