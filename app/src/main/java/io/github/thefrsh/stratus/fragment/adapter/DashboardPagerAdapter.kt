package io.github.thefrsh.stratus.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.thefrsh.stratus.activity.DashboardActivity
import io.github.thefrsh.stratus.fragment.ContactsFragment
import io.github.thefrsh.stratus.fragment.NotificationsFragment
import io.github.thefrsh.stratus.fragment.UserSearchFragment

class DashboardPagerAdapter(private val dashboardActivity: DashboardActivity)
    : FragmentStateAdapter(dashboardActivity) {

    companion object {
        private const val NUMBER_OF_PAGES = 3
    }

    override fun getItemCount(): Int {
        return NUMBER_OF_PAGES
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ContactsFragment(dashboardActivity.contactsViewModel)
            1 -> UserSearchFragment()
            else -> NotificationsFragment()
        }
    }
}
