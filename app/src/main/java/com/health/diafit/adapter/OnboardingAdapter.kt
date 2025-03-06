package com.health.diafit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.health.diafit.ui.fragment.onboarding.LastOnboardingFragment
import com.health.diafit.ui.fragment.onboarding.FirstOnboardingFragment
import com.health.diafit.ui.fragment.onboarding.SecondOnboardingFragment
import com.health.diafit.ui.fragment.onboarding.ThirdOnboardingFragment

class OnboardingAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val pages = listOf(
        FirstOnboardingFragment(),
        SecondOnboardingFragment(),
        ThirdOnboardingFragment(),
        LastOnboardingFragment()
    )

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstOnboardingFragment()
            1 -> SecondOnboardingFragment()
            2 -> ThirdOnboardingFragment()
            3 -> LastOnboardingFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
