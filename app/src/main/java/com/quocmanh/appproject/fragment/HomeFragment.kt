package com.quocmanh.appproject.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.quocmanh.appproject.activity.CartActivity
import com.quocmanh.appproject.activity.DetailProductActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.activity.SearchActivity
import com.quocmanh.appproject.adapter.*
import com.quocmanh.appproject.databinding.FragmentHomeBinding

import com.quocmanh.appproject.model.Avatar
import com.quocmanh.appproject.model.ImageList
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.model.ProductModel
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeFragment : Fragment(), ProductAdapter.IProductData, LaptopAdapter.ILaptopData,
    PhoneAdapter.IPhoneData, TabletAdapter.ITabletData, HeadphoneAdapter.IHeadphoneData,
    ScreenAdapter.IScreenData, MicrophoneAdapter.IMicroData, View.OnClickListener {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var dataList : MutableList<Product>
    private lateinit var productModel : ProductModel
    private lateinit var productList : MutableList<Product>
    private lateinit var callApi : CallApi
    private lateinit var laptopList : MutableList<Product>
    private lateinit var sharedPreferences: SharedPreferences
    var userName : String = ""
    var idUser : Int? = null
    private lateinit var listAvatar : MutableList<Avatar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        setupViewFlipper()
        setupViewFlipper1()

        getAllProduct()

        return binding.root
    }
    private fun init() {
        dataList = mutableListOf()
        callApi = RetrofitFactor.createRetrofit()
        productModel = ProductModel()
        productList = mutableListOf()
        laptopList = mutableListOf()
        binding.civToCart.setOnClickListener(this)
        binding.txtSearch.setOnClickListener(this)
        binding.civSearchAdvanced.setOnClickListener(this)
        sharedPreferences = requireContext().applicationContext.getSharedPreferences("user", MODE_PRIVATE)
        userName = sharedPreferences.getString("name_user", "").toString()
        idUser = sharedPreferences.getString("id_user", "")!!.toInt()
        listAvatar = mutableListOf()
        getAvatar()
    }
    private fun getAvatar() {
        binding.txtName.text = userName
        callApi.getAvatarFromId(idUser!!.toInt())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listAvatar = it.data
                Glide.with(binding.root)
                    .load(listAvatar[0].avatar)
                    .into(binding.imgAvatar)
            },{

            })
    }
    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.civ_to_cart -> {
                val intent = Intent()
                intent.setClass(requireContext(), CartActivity::class.java)
                startActivity(intent)
            }
            R.id.txt_search -> {
                val intent = Intent()
                intent.setClass(requireContext(), SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.civ_search_advanced -> {
                val searchAdvancedFragment = SearchAdvancedFragment()
                val fm = parentFragmentManager
                val fg = fm.beginTransaction()
                fg.replace(R.id.fr_layout_main, searchAdvancedFragment)
                fg.addToBackStack(null)
                fg.commit()
            }
        }
    }

    private fun getAllProduct(){
        callApi.getAllProduct()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val message = it.message
                dataList = it.data!!
                if(message == "success"){

                    productList = dataList
                    binding.rcProduct.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcProduct.adapter = ProductAdapter(this)

                    for(i in 0..productList.size-1){
                        if(productList[i].productCode == "LT1"){
                            var id : Int = productList[i].id!!
                            var productCode : String = productList[i].productCode!!
                            var brandCode : String = productList[i].brandCode!!
                            var name : String = productList[i].nameProduct!!
                            var image : String = productList[i].image!!
                            var price : String = productList[i].price!!
                            var productParam : String = productList[i].productParameter!!
                            var descrip : String = productList[i].description!!

                            laptopList.add(Product(id, productCode, brandCode, name, image, price, productParam, descrip))
                        }
                    }
                    binding.rcLaptop.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcLaptop.adapter = LaptopAdapter(this)

                    binding.rcPhone.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcPhone.adapter = PhoneAdapter(this)

                    binding.rcTablet.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcTablet.adapter = TabletAdapter(this)

                    binding.rcHeadphone.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcHeadphone.adapter = HeadphoneAdapter(this)

                    binding.rcScreen.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcScreen.adapter = ScreenAdapter(this)

                    binding.rcMicrophone.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rcMicrophone.adapter = MicrophoneAdapter(this)

                    }
            },{

            })
    }
    private fun setupViewFlipper() {
        var imageList : MutableList<ImageList> = mutableListOf()
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/khai-truong-0012-sli-tay-ninh.jpg"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/s23-sliding-tra-hang.png"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/tivi-xiaomi-p1-55-sliding.png"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/fold-4-00241.png"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/s23-sliding-tra-hang.png"))
        for(i in 0..imageList.size-1){
            val imageView = ImageView(requireContext())
            Glide.with(requireContext())
                .load(imageList[i].image)
                .into(imageView)
            imageView.setScaleType(ImageView.ScaleType.FIT_XY)
            binding.viewFlipper.addView(imageView)
        }
        binding.viewFlipper.setFlipInterval(1500)
        binding.viewFlipper.setAutoStart(true)
        val adIn : Animation = AnimationUtils.loadAnimation(context,
            android.R.anim.slide_in_left
        )
        val adOut : Animation = AnimationUtils.loadAnimation(context,
            android.R.anim.slide_out_right
        )
        binding.viewFlipper.setAnimation(adIn)
        binding.viewFlipper.setAnimation(adOut)
    }
    private fun setupViewFlipper1() {
        var imageList : MutableList<ImageList> = mutableListOf()
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/soundpeats-mini-pro-hs-slide.jpg"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/tivi-xiaomi-p1-55-sliding.png"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/redmi%20band%202.png"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/asus%20ruoc%20qua.jpg"))
        imageList.add(ImageList("https://cdn2.cellphones.com.vn/690x300,webp,q100/https://dashboard.cellphones.com.vn/storage/Rigright-109-00234.jpg"))
        for(i in 0..imageList.size-1){
            val imageView = ImageView(requireContext())
            Glide.with(requireContext())
                .load(imageList[i].image)
                .into(imageView)
            imageView.setScaleType(ImageView.ScaleType.FIT_XY)
            binding.viewFlipper1.addView(imageView)
        }
        binding.viewFlipper1.setFlipInterval(1500)
        binding.viewFlipper1.setAutoStart(true)
        val adIn : Animation = AnimationUtils.loadAnimation(context,
            android.R.anim.slide_in_left
        )
        val adOut : Animation = AnimationUtils.loadAnimation(context,
            android.R.anim.slide_out_right
        )
        binding.viewFlipper1.setAnimation(adIn)
        binding.viewFlipper1.setAnimation(adOut)
    }

    override fun getCountProduct(): Int {
        return productList.size
    }

    override fun getDataProduct(position: Int): Product {
        return productList[position]
    }

    override fun onClickItem(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("brand_code", productList[position].brandCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getCountLaptop(): Int {
        return laptopList.size
    }

    override fun getDataLaptop(position: Int): Product {
        return laptopList[position]
    }

    override fun onClickItemLaptop(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getCountPhone(): Int {
        return laptopList.size
    }

    override fun getDataPhone(position: Int): Product {
        return laptopList[position]
    }

    override fun onClickItemPhone(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getCountTablet(): Int {
        return laptopList.size
    }

    override fun getDataTablet(position: Int): Product {
        return laptopList[position]
    }

    override fun onClickItemTablet(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getCountHeadphone(): Int {
        return laptopList.size
    }

    override fun getDataHeadphone(position: Int): Product {
        return laptopList[position]
    }

    override fun onClickItemHeadphone(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getCountScreen(): Int {
        return laptopList.size
    }

    override fun getDataScreen(position: Int): Product {
        return laptopList[position]
    }

    override fun onClickItemScreen(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getCountMicro(): Int {
        return laptopList.size
    }

    override fun getDataMicro(position: Int): Product {
        return laptopList[position]
    }

    override fun onClickItemMicro(position: Int) {
        val intent = Intent()
        intent.setClass(requireContext(), DetailProductActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", productList[position].id!!.toInt())
        bundle.putString("product_code", productList[position].productCode)
        bundle.putString("product_name", productList[position].nameProduct)
        bundle.putString("image", productList[position].image)
        bundle.putString("price", productList[position].price)
        bundle.putString("product_patameters", productList[position].productParameter)
        bundle.putString("description", productList[position].description)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}