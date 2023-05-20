package com.quocmanh.appproject.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.activity.DetailFavoriteActivity
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.ProductFavoriteAdapter
import com.quocmanh.appproject.model.ProductFavorite
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoriteFragment : Fragment(), ProductFavoriteAdapter.IDataFavorite, View.OnClickListener {
    private lateinit var callApi: CallApi
    private lateinit var mView : View
    private lateinit var rcView : RecyclerView
    private lateinit var shared : SharedPreferences
    private lateinit var dataFavorite : MutableList<ProductFavorite>
    private lateinit var imbBack : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite, container, false)
        initView(mView)
        getDataFavorite()
        return mView
    }
    private fun initView(view : View){
        callApi = RetrofitFactor.createRetrofit()
        rcView = view.findViewById(R.id.rcv_product_favorite)
        shared = requireContext().getSharedPreferences("user", MODE_PRIVATE)
        imbBack = view.findViewById(R.id.imb_back_favorite)
        imbBack.setOnClickListener(this)
    }
    private fun getDataFavorite(){
        val idUser = shared.getString("id_user", "")!!.toInt()
        callApi.getDataFavorite(idUser)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dataFavorite = it.dataCart
                val adapter = ProductFavoriteAdapter(this)
                rcView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rcView.adapter = adapter
            },{

            })
    }

    override fun getItemCountFavorite(): Int {
        return dataFavorite.size
    }

    override fun getDataFavorite(position: Int): ProductFavorite {
        return dataFavorite[position]
    }

    override fun onClickItemFavorite(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailFavoriteActivity::class.java)
        val idPro = dataFavorite[position].idProduct
        val bundle = Bundle()
        bundle.putInt("id_product", idPro!!)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imb_back_favorite -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}