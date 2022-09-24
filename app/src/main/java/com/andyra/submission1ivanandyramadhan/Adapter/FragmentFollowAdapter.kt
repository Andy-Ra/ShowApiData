package com.andyra.submission1ivanandyramadhan.Adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andyra.submission1ivanandyramadhan.View.Follow.FollowFragment

class FragmentFollowAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var followuser: String = ""
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowFragment.EXTRA_USERNAME, followuser)
        }
        return fragment
    }
}