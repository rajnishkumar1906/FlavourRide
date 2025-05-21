package com.example.falvourride

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Home::class.java))
            finish()
        },3000)
    }
}