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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.DetailNewsActivity
import com.quocmanh.appproject.activity.DetailNewsFavoriteActivity
import com.quocmanh.appproject.adapter.PostFavoriteAdapter
import com.quocmanh.appproject.model.PostFavorite
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoriteNewsFragment : Fragment(), View.OnClickListener, PostFavoriteAdapter.INewsData {
    private lateinit var imvBack : ImageView
    private lateinit var rcNews : RecyclerView
    private lateinit var mView : View
    private lateinit var callApi: CallApi
    private lateinit var sharedPreferences: SharedPreferences
    private var id : Int? = null
    private lateinit var list : MutableList<PostFavorite>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_news, container, false)
        init(mView)
        getData()
        return mView
    }

    fun getData() {
        callApi.getNewsFavoriteFromIdUser(id.toString())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val message = it.message
                if(message.equals("ok")) {
                    list = it.dataList
//                    if (list.size != 0) {
                        val postFavoriteAdapter = PostFavoriteAdapter(this)
                        rcNews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        rcNews.adapter = postFavoriteAdapter
//                    } else {
//                        Toast.makeText(requireContext(), "Không dữ liệu!", Toast.LENGTH_LONG).show()
//                    }
                }
            },{

            })
    }

    fun init(view : View) {
        imvBack = view.findViewById(R.id.imv_back_news_favorite)
        rcNews = view.findViewById(R.id.rcv_news_favorite)
        imvBack.setOnClickListener(this)
        callApi = RetrofitFactor.createRetrofit()
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        id = sharedPreferences.getString("id_user", "")!!.toInt()
        list = mutableListOf()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imv_back_news_favorite -> {
                val accountFragment = AccountFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, accountFragment)
                fg.commit()
            }
        }
    }

    override fun getCountNews(): Int {
        return list.size
    }

    override fun getDataNews(position: Int): PostFavorite {
        return list[position]
    }

    override fun onClickItemNews(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailNewsFavoriteActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", list[position].id!!.toInt())
        bundle.putString("image_post", list[position].image.toString())
        bundle.putString("title", list[position].title.toString())
        bundle.putString("content", list[position].content.toString())
        bundle.putString("time_create", list[position].time.toString())
        bundle.putInt("id_user", list[position].idUser!!.toInt())
        intent.putExtras(bundle)
        startActivity(intent)
    }
}