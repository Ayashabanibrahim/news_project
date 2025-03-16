package com.example.newsapplication.uiModel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.newsapplication.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.screen_splash)
        val lottie=findViewById<LottieAnimationView>(R.id.lottie)
//        val animator=ObjectAnimator.ofFloat(lottie,"translationX",2000f)
//        animator.duration=3000
//        animator.startDelay=1000
//        animator.interpolator=AccelerateDecelerateInterpolator()
//        animator.start()

//        lottie.animate().translationX(1000F).setDuration(2000).setStartDelay(500)
//            .withEndAction {
//                lottie.animate().alpha(0F).setDuration(1000).start()
//            }.start()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//
//        )
        Handler(Looper.getMainLooper()).postDelayed({
//            val fadeOut=ObjectAnimator.ofFloat(lottie,"alpha",1f,0f).apply {
//                duration=1000
//            }
//            fadeOut.start()
            startActivity(Intent(this, MainActivity::class.java))
        },3000)
    }
}
