package com.aysesenses.creditcalculationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SplashScreenActivity : AppCompatActivity() {

    private val splashImage by lazy { findViewById<View>(R.id.splash_image) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashImage.alpha = 0f
        splashImage.animate().setDuration(2000).alpha(1f).withEndAction {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}