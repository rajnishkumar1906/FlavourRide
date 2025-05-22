package com.example.falvourride

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {

    private lateinit var appName: TextView
    private lateinit var animatedText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash)

        appName = findViewById(R.id.appName)
        animatedText = findViewById(R.id.tvAnimatedText)

        // Fade in animation for app name
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 2000
        appName.startAnimation(fadeIn)

        startTypewriterEffect("Your favourite dish is waiting for you......")
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Home::class.java))
            finish()
        }, 3000)
    }

    private fun startTypewriterEffect(text: String) {
        val delay: Long = 50
        animatedText.text = ""
        val handler = Handler(Looper.getMainLooper())

        for (i in text.indices) {
            handler.postDelayed({
                animatedText.append(text[i].toString())
            }, delay * i)
        }
    }
}
