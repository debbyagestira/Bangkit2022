package com.dicoding.mysubmission2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.mysubmission2.fragment.FollowersFragment
import com.dicoding.mysubmission2.fragment.FollowingFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity, private val data: String) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment().apply {
                username = data
            }
            1 -> fragment = FollowingFragment().apply {
                username = data
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}