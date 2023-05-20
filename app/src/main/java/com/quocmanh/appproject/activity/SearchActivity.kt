package com.quocmanh.appproject.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.*
import com.quocmanh.appproject.model.Product
import com.quocmanh.appproject.myinterface.CallApi
import com.quocmanh.appproject.myinterface.RetrofitFactor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchActivity : AppCompatActivity(), SearchAdapter.ISearchData {
    lateinit var courseRV: RecyclerView
    lateinit var courseRVAdapter: SearchAdapter
    lateinit var courseList: ArrayList<Product>
    private lateinit var callApi: CallApi
    lateinit var productList : ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        callApi = RetrofitFactor.createRetrofit()
        courseRV = findViewById(R.id.idRVCourses)
        courseList = ArrayList()
        productList = ArrayList()

        courseRVAdapter = SearchAdapter(this, productList)
        courseRV.adapter = courseRVAdapter
        //get data
        getData()
        courseRVAdapter.notifyDataSetChanged()
    }
    private fun getData() {
        callApi.getAllProduct1()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val message = it.message
                courseList = it.data!!
                if(message == "success"){
                    productList = courseList
                    courseRVAdapter = SearchAdapter(this, productList)
                    courseRV.layoutManager = GridLayoutManager(this, 2, VERTICAL, false)
                    courseRV.adapter = courseRVAdapter
                    courseRVAdapter.notifyDataSetChanged()
                }
            },{

            })

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Product> = ArrayList()

        // running a for loop to compare elements.
        for (item in productList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.nameProduct!!.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            courseRVAdapter.filterList(filteredlist)

            productList = filteredlist
//            courseRVAdapter = SearchAdapter(this, productList)
//            courseRV.layoutManager = GridLayoutManager(this, 2, VERTICAL, false)
//            courseRV.adapter = courseRVAdapter
            courseRVAdapter.notifyDataSetChanged()
//            productList = courseList
            val handler = Handler()
            handler.postDelayed(Runnable {
                productList = courseList
            }, 6000)
        }
    }

    override fun onClickItemSearch(position: Int) {
        val intent = Intent()
        intent.setClass(this, DetailProductActivity::class.java)
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