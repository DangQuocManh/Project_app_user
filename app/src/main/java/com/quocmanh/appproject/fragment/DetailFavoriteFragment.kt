package com.quocmanh.appproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.quocmanh.appproject.activity.DetailFavoriteActivity
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.FavoriteDetailAdapter
import com.quocmanh.appproject.model.Product

class DetailFavoriteFragment : Fragment(), View.OnClickListener {
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager
    private lateinit var mView : View
    private lateinit var txtNameFavorite : TextView
    private lateinit var txtPriceFavorite : TextView
    private lateinit var imvProductFavorite : ImageView
    private lateinit var detailFavoriteActivity: DetailFavoriteActivity
    private lateinit var dataProduct : MutableList<Product>
    private lateinit var imbBack : ImageButton
    lateinit var data : Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_favorite, container, false)
        init(mView)
        detailFavoriteActivity = activity as DetailFavoriteActivity
        data = detailFavoriteActivity.data1
        txtNameFavorite.text = data.nameProduct
        txtPriceFavorite.text = data.price
        Glide.with(requireContext())
            .load(data.image)
            .into(imvProductFavorite)
        getTabLayout()
        return mView
    }
    private fun init(view : View) {
        tabLayout = view.findViewById(R.id.tabLayout_favorite)
        viewPager = view.findViewById(R.id.viewPager_favorite)
        txtNameFavorite = view.findViewById(R.id.txt_name_product_favorite)
        txtPriceFavorite = view.findViewById(R.id.txt_price_favorite)
        imvProductFavorite = view.findViewById(R.id.imv_image_favorite)
        dataProduct = mutableListOf()
        imbBack = view.findViewById(R.id.imb_back_favorite_prod)
        imbBack.setOnClickListener(this)
        data = Product()
    }
    private fun getTabLayout(){
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Cấu hình tham số"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Mô tả"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sản phẩm liên quan"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = FavoriteDetailAdapter(requireContext(), parentFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imb_back_favorite_prod -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}