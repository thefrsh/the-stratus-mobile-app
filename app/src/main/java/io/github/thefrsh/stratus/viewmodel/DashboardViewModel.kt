package io.github.thefrsh.stratus.viewmodel

import androidx.databinding.DataBindingUtil
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.activity.DashboardActivity
import io.github.thefrsh.stratus.databinding.ActivityDashboardBinding
import io.github.thefrsh.stratus.fragment.adapter.DashboardPagerAdapter
import javax.inject.Inject

class DashboardViewModel @Inject constructor() {

    private lateinit var binding: ActivityDashboardBinding

    fun setContentView(activity: DashboardActivity, layoutId: Int) {

        this.binding = DataBindingUtil.setContentView(activity, layoutId)

        setPagerAdapter(activity)
        integrateViewPagerWithBottomNavigation()
    }

    private fun setPagerAdapter(activity: DashboardActivity) {

        val viewPager = binding.dashboardViewPager

        viewPager.isUserInputEnabled = false
        viewPager.adapter = DashboardPagerAdapter(activity)
    }

    private fun integrateViewPagerWithBottomNavigation() {

        val viewPager = binding.dashboardViewPager

        binding.dashboardBottomNavigation.setOnNavigationItemSelectedListener { item ->

            when(item.itemId) {

                R.id.bottomnav_contacts_tab -> {
                    viewPager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottomnav_search_user_tab -> {
                    viewPager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottomnav_notifications_tab -> {
                    viewPager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }
    }
}
