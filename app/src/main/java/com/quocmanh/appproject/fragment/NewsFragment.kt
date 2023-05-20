package com.quocmanh.appproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.DetailNewsActivity
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.adapter.LaptopAdapter
import com.quocmanh.appproject.adapter.NewsAdapter
import com.quocmanh.appproject.model.Post
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.model.news
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsFragment : Fragment(), View.OnClickListener, NewsAdapter.INewsData {
    private lateinit var imvBackNews: ImageView
    private lateinit var rcNews: RecyclerView
    private lateinit var mView: View
    private lateinit var listNews: MutableList<Post>
    private lateinit var callApi: CallApi
    private lateinit var imvAddNews : ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news, container, false)
        init(mView)
        getData()
        return mView
    }

    private fun init(view: View) {
        imvBackNews = view.findViewById(R.id.imv_back_news)
        rcNews = view.findViewById(R.id.rcv_news)
        imvBackNews.setOnClickListener(this)
        listNews = mutableListOf()
        callApi = RetrofitFactor.createRetrofit()
        imvAddNews = view.findViewById(R.id.imv_add_post_new)
        imvAddNews.setOnClickListener {
            val fragmentAddPostFragment = AddPostFragment()
            val fm = parentFragmentManager
            val fg = fm.beginTransaction()
            fg.replace(R.id.fr_layout_main, fragmentAddPostFragment)
            fg.addToBackStack(null)
            fg.commit()
        }
    }

    fun getData() {
        callApi.getAllPost()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val mess = it.message
                if (mess.equals("ok")) {
                    listNews = it.data
                    rcNews.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    rcNews.adapter = NewsAdapter(this)
                }
            }, {

            })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imv_back_news -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getCountNews(): Int {
        return listNews.size
    }

    override fun getDataNews(position: Int): Post {
        return listNews[position]
    }

    override fun onClickItemNews(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailNewsActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", listNews[position].id!!.toInt())
        bundle.putString("image_post", listNews[position].imagePost)
        bundle.putString("title", listNews[position].title)
        bundle.putString("content", listNews[position].content)
        bundle.putString("time_create", listNews[position].timeCreate)
        bundle.putInt("id_user", listNews[position].idUser!!.toInt())
        intent.putExtras(bundle)
        startActivity(intent)
    }

}