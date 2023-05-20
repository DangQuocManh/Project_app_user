package com.quocmanh.appproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.appproject.R
import com.quocmanh.appproject.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getFragment()
    }
    private fun getFragment(){
        val loginFragment = LoginFragment()
        val fm = supportFragmentManager
        val fg = fm.beginTransaction()
        fg.add(R.id.fr_login_register, loginFragment)
//        fg.addToBackStack(null)
        fg.commit()
    }
}