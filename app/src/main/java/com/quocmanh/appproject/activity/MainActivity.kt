package com.quocmanh.appproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.quocmanh.appproject.R
import com.quocmanh.appproject.databinding.ActivityMainBinding
import com.quocmanh.appproject.fragment.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getFragment()
        getBottomNavigation()
    }
    private fun getBottomNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_main_page -> {
                    val homeFragment : HomeFragment = HomeFragment()
                    loadFragment(homeFragment)
                    true
                }
                R.id.navigation_category -> {
                    val categoryFragment = CategoryFragment()
                    loadFragment(categoryFragment)
                    true
                }
                R.id.navigation_favorite -> {
                    val favoriteFragment = FavoriteFragment()
                    loadFragment(favoriteFragment)
                    true
                }
                R.id.navigation_news -> {
                    val newsFragment = NewsFragment()
                    loadFragment(newsFragment)
                    true
                }
                R.id.navigation_account -> {
                    val accountFragment = AccountFragment()
                    loadFragment(accountFragment)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
    private fun loadFragment(fragment : Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val fg : FragmentTransaction = fragmentManager.beginTransaction()
        fg.replace(R.id.fr_layout_main, fragment)
        fg.addToBackStack(null)
        fg.commit()
    }
    private fun getFragment(){
        val homeFragment = HomeFragment()
        val fragmentManager = supportFragmentManager
        val fg : FragmentTransaction = fragmentManager.beginTransaction()
        fg.add(R.id.fr_layout_main, homeFragment)
        fg.addToBackStack(null)
        fg.commit()
    }
}