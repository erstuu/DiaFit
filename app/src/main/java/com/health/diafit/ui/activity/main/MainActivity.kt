package com.health.diafit.ui.activity.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.health.diafit.R
import com.health.diafit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupNavigationController(binding)
    }

    private fun setupNavigationController(binding: ActivityMainBinding) {
        val navView: BottomNavigationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.inputDataFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.inputDataAdvancedFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.resultFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.newsFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.editProfileFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.typeDiabetesFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.firstTypeDiabetesFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.secondTypeDiabetesFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.gestasionalTypeDiabetesFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.tipsFoodFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.tipsFoodMenuFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.tipsFoodPageFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.workoutRecommendFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.complicationDiabetesFragment -> {
                    navView.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }
}