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
import com.quocmanh.appproject.activity.DetailProductActivity
import com.quocmanh.appproject.activity.MainActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.ProductDetailAdapter
import com.quocmanh.appproject.model.Product
import java.text.DecimalFormat

class DetailProductFragment : Fragment(), View.OnClickListener {
    private lateinit var txtNameProduct : TextView
    private lateinit var txtPriceProduct : TextView
    private lateinit var imvProduct : ImageView
    private lateinit var mView: View
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager : ViewPager
    private lateinit var detailProductActivity: DetailProductActivity
    private lateinit var product : Product
    private lateinit var imbBack : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_detail_product, container, false)
        detailProductActivity = activity as DetailProductActivity
        product = detailProductActivity.listProduct
        initUi(mView)
        getTabLayout()
        txtNameProduct.text = product.nameProduct
        val decimalFormat = DecimalFormat("###,###,###")
        txtPriceProduct.text = "Giá: " + decimalFormat.format(product.price!!.toLong()).toString() + " Đ"
        Glide.with(requireContext())
            .load(product.image)
            .into(imvProduct)
        return mView
    }

    private fun getTabLayout(){
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Cấu hình tham số"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Mô tả"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sản phẩm liên quan"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ProductDetailAdapter(requireContext(), parentFragmentManager, tabLayout!!.tabCount)
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
    private fun initUi(view : View){
        txtNameProduct = view.findViewById(R.id.txt_name_product)
        txtPriceProduct = view.findViewById(R.id.txt_price)
        imvProduct = view.findViewById(R.id.imv_image_product_detail)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        imbBack = view.findViewById(R.id.imb_back_detail_prod)
        imbBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.imb_back_detail_prod -> {
                val intent = Intent()
                intent.setClass(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}