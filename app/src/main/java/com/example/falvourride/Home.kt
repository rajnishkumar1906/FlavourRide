package com.example.falvourride

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            loadFragment(HomeFrag())
            bottomNav.selectedItemId = R.id.nav_menu
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu -> loadFragment(HomeFrag())
                R.id.nav_orders -> loadFragment(OrderFrag())
                R.id.nav_offers -> loadFragment(OffersFrag())
            }
            true
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
