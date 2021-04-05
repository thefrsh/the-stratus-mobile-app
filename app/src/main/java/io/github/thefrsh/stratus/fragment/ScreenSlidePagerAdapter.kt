package io.github.thefrsh.stratus.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    companion object {
        private const val NUMBER_OF_PAGES = 3
    }

    override fun getItemCount(): Int {
        return NUMBER_OF_PAGES
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ContactsFragment()
            1 -> UserSearchFragment()
            else -> NotificationsFragment()
        }
    }
}
