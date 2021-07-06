package com.example.bluetoothchat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.builders.bluetoothchat.MainActivity
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.AppController

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initSplash()
    }

    private fun initSplash() {
        Handler().postDelayed({
            handleNextActivity()
        }, 2000)
    }

    private fun handleNextActivity() {
        var intent: Intent

        if (AppController.currentUser == null)
            intent = Intent(this, ProfileActivity::class.java)
        else
            intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
        finish()
    }
}