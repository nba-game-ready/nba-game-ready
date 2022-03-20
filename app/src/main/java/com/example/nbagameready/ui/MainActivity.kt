package com.example.nbagameready.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nbagameready.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        animateGlobe()
    }

    private fun animateGlobe() {
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        val rotatingBall = findViewById<ImageView>(R.id.ball_bounce)

        rotatingBall.animation = rotate
    }

}


