package com.example.nbagameready

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    /*private lateinit var rotatingBall: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotatingBall = findViewById(R.id.ball_bounce)
        animateGlobe()
    }

    private fun animateGlobe() {
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        rotatingBall.animation = rotate
    }
}*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}