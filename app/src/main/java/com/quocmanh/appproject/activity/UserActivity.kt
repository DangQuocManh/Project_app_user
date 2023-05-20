package com.quocmanh.appproject.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.quocmanh.appproject.R
import com.quocmanh.appproject.databinding.ActivityMainBinding
import com.quocmanh.appproject.databinding.ActivityUserBinding
import com.quocmanh.appproject.fragment.*

class UserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        getFragment()
        getBottomNavigation()
    }
    private fun getBottomNavigation(){
        binding.bottomNavigationUser.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_main_page_user -> {
                    val homeFragment: HomeUserFragment = HomeUserFragment()
                    loadFragment(homeFragment)
                    true
                }
                R.id.navigation_category_user -> {
                    val homeFragment: CategoryUserFragment = CategoryUserFragment()
                    loadFragment(homeFragment)
                    true
                }
                R.id.navigation_favorite_user -> {
                    val viewDialog = ViewDialog()
                    viewDialog.showDialog(this, baseContext)
                    true
                }

                R.id.navigation_news_user -> {
                    val viewDialog = ViewDialog()
                    viewDialog.showDialog(this, baseContext)
                    true
                }
                R.id.navigation_account_user -> {
                    val viewDialog = ViewDialog()
                    viewDialog.showDialog(this, baseContext)
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
        fg.replace(R.id.fr_layout_main_user, fragment)
        fg.addToBackStack(null)
        fg.commit()
    }
    private fun getFragment(){
        val homeFragment = HomeUserFragment()
        val fragmentManager = supportFragmentManager
        val fg : FragmentTransaction = fragmentManager.beginTransaction()
        fg.add(R.id.fr_layout_main_user, homeFragment)
        fg.addToBackStack(null)
        fg.commit()
    }
}