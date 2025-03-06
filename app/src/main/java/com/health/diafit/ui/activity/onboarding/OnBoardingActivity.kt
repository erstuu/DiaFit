package com.health.diafit.ui.activity.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.health.diafit.R
import com.health.diafit.adapter.OnboardingAdapter
import com.health.diafit.data.local.UserPreference
import com.health.diafit.databinding.ActivityFirstOnBoardingBinding
import com.health.diafit.ui.activity.main.MainActivity
import com.health.diafit.ui.activity.main.MainViewModel
import com.health.diafit.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private var _binding: ActivityFirstOnBoardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: OnboardingAdapter
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFirstOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainOnboarding) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val language = viewModel.getLanguageSync()
        LocaleHelper.updateLocale(this, language)

        userPreference = UserPreference(this)

        setupViewPager()
        setupButtons()

        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onLastPage(position)
            }
        })
    }

    private fun setupButtons() {
        binding.tvSkip.setOnClickListener {
            binding.viewPager.currentItem = adapter.itemCount - 1
        }

        binding.btnNextOnboarding.setOnClickListener {
            val currentPosition = binding.viewPager.currentItem

            if (currentPosition < adapter.itemCount - 1) {
                binding.viewPager.currentItem = currentPosition + 1
            } else {
                finishOnboarding()
            }
        }
    }

    private fun onLastPage(position: Int) {
        if (position == adapter.itemCount - 1) {
            binding.btnNextOnboarding.text = getString(R.string.mulai)
            binding.tvSkip.visibility = View.GONE
        } else {
            binding.btnNextOnboarding.text = getString(R.string.lanjutkan)
            binding.tvSkip.visibility = View.VISIBLE
        }
    }

    private fun finishOnboarding() {
        runBlocking {
            userPreference.setOnboardingCompleted()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}