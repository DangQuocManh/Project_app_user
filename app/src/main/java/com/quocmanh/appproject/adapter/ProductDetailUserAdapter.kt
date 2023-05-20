package com.quocmanh.appproject.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.quocmanh.appproject.fragment.*

class ProductDetailUserAdapter(private val myContext: Context, var fm : FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm)  {
    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return ParameterUserFragment()
            }
            1 -> {
                //return SportFragment()
                return DescriptionUserFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                //return MovieFragment()
                return ProductConcernUserFragment()
            }
            else -> return ParameterUserFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}
