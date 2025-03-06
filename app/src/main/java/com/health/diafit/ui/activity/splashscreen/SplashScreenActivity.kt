package com.health.diafit.ui.activity.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.health.diafit.data.local.UserPreference
import com.health.diafit.databinding.ActivitySplashScreenBinding
import com.health.diafit.ui.activity.main.MainActivity
import com.health.diafit.ui.activity.onboarding.OnBoardingActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Suppress("customSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val userPreference = UserPreference(applicationContext)
            val isOnboardingCompleted = runBlocking {
                userPreference.isOnboardingCompleted().first()
            }
            val intent = if (isOnboardingCompleted) {
                Intent(this@SplashScreenActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)

    }
}

