package com.quocmanh.appproject.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.quocmanh.appproject.R
import com.quocmanh.appproject.adapter.SearchAdapter
import com.quocmanh.appproject.model.Product


class SearchFragment : Fragment() {
    lateinit var courseRV: RecyclerView
    lateinit var courseRVAdapter: SearchAdapter
    lateinit var courseList: ArrayList<Product>
    private lateinit var mView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false)
        courseRV = mView.findViewById(R.id.idRVCourses)
        courseList = ArrayList()

//        courseRVAdapter = SearchAdapter(courseList)
//        courseRV.adapter = courseRVAdapter
//        //get data
//        getData()
        courseRVAdapter.notifyDataSetChanged()
        return mView
    }


    private fun getData() {
        courseList.add(Product(1, "java", "Samsung", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
                   "123000000", "test", "test"))
        courseList.add(Product(1, "java", "Iphone", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
                   "123000000", "test", "test"))
        courseList.add(Product(1, "java", "Sony", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
            "123000000", "test", "test"))
        courseList.add(Product(1, "java", "Oppo", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
            "123000000", "test", "test"))
        courseList.add(Product(1, "java", "Dell", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
            "123000000", "test", "test"))
        courseList.add(Product(1, "java", "Laptop", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
            "123000000", "test", "test"))
        courseList.add(Product(1, "java", "Phone", "https://cdn.mos.cms.futurecdn.net/THvEKngiJ5y7oiC3pUeyRi.jpg",
            "123000000", "test", "test"))

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

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
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Product> = ArrayList()

        // running a for loop to compare elements.
        for (item in courseList) {
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
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            courseRVAdapter.filterList(filteredlist)
        }
    }

}