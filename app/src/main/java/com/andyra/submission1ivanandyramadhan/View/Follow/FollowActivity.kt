package com.andyra.submission1ivanandyramadhan.View.Follow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.andyra.submission1ivanandyramadhan.Adapter.FragmentFollowAdapter
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.databinding.ActivityFollowBinding
import com.google.android.material.tabs.TabLayoutMediator

class FollowActivity : AppCompatActivity() {
    private lateinit var mbinding: ActivityFollowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityFollowBinding.inflate(layoutInflater)
        setTitle(R.string.detailuser)
        setContentView(mbinding.root)

        val mFollowAdapter = FragmentFollowAdapter(this)
        mFollowAdapter.followuser = intent.getStringExtra(EXTRA_USER).toString()
        mbinding.apply {
            vpfollow.adapter = mFollowAdapter
            TabLayoutMediator(tabsfollow, vpfollow){ mtab, mpos ->
                mtab.text = resources.getString(TAB_TITLES[mpos])
            }.attach()
        }
        supportActionBar?.elevation = 0f
    }

    companion object {
        const val EXTRA_USER = "extrauser"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.follower
        )
    }
}