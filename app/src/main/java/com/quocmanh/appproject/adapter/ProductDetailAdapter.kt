package com.quocmanh.appproject.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.quocmanh.appproject.fragment.DescriptionFragment
import com.quocmanh.appproject.fragment.ParameterFragment
import com.quocmanh.appproject.fragment.ProductConcernFragment

class ProductDetailAdapter(private val myContext: Context, var fm : FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm)  {
    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return ParameterFragment()
            }
            1 -> {
                //return SportFragment()
                return DescriptionFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                //return MovieFragment()
                return ProductConcernFragment()
            }
            else -> return ParameterFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}
