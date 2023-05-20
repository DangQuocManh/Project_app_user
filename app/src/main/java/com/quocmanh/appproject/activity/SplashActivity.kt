package com.quocmanh.appproject.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.quocmanh.appproject.R

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val name : String = sharedPreferences.getString("name_user", "").toString()
        val handler = Handler()
        handler.postDelayed(Runnable {
            if (name.equals("") || name == null || name.isEmpty()) {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }, 2500)
    }
}