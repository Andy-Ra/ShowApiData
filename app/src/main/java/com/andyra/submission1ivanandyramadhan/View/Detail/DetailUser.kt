package com.andyra.submission1ivanandyramadhan.View.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.andyra.submission1ivanandyramadhan.Adapter.FragmentFollowAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity() {
    private lateinit var mBinding: ActivityDetailUserBinding
    var username_detail: String = ""
    var arraytab : Array<String> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.detailuser)
        mBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        showLoading(true)

        getusername()
        putprocess()
    }

    private fun putprocess() {
        val mclient = ApiConfig.getApiService().getDetail(username_detail)
        mclient.enqueue(object : Callback<ProfileData> {
            override fun onResponse(
                call: Call<ProfileData>,
                response: Response<ProfileData>
            ) {
                if (response.isSuccessful) {
                    val mresponbody = response.body()
                    if (mresponbody != null) {
                        showLoading(false)
                        mBinding.apply {
                            if (mresponbody.location == null) {
                                tvdlocation.text = StringBuilder(" - ")
                            } else {
                                tvdlocation.text = StringBuilder(" ").append(mresponbody.location)
                            }

                            if (mresponbody.company == null) {
                                tvdcompany.text = StringBuilder(" - ")

                            } else {
                                tvdcompany.text = StringBuilder(" ").append(mresponbody.company)
                            }
                            Glide.with(root)
                                .load(mresponbody.avatarUrl)
                                .circleCrop()
                                .into(imgdetail)

                            tvdname.text = mresponbody.name
                            tvduname.text = StringBuilder("@").append(mresponbody.login)
                            tvdrepo.text = " ${mresponbody.publicRepos} ${getString(R.string.repo)}"
                        }
                        arraytab = arrayOf(
                            "${getString(R.string.following)} (${mresponbody.following})",
                            "${getString(R.string.follower)} (${mresponbody.followers})"
                        )
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    showfollower()
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun showfollower() {
        val mFollowAdapter = FragmentFollowAdapter(this)
        mFollowAdapter.followuser = username_detail
        mBinding.apply {
            vpfollow.adapter = mFollowAdapter
            TabLayoutMediator(tabsfollow, vpfollow) { mtab, mpos ->
                mtab.text = arraytab[mpos]
            }.attach()
        }
        supportActionBar?.elevation = 0f
    }

    private fun getusername() {
        username_detail = intent.getStringExtra(EXTRA_LOGIN).toString()
    }

    private fun showLoading(mload: Boolean) {
        if (mload) {
            mBinding.pgdetail.visibility = View.VISIBLE
        } else {
            mBinding.pgdetail.visibility = View.INVISIBLE
        }
    }
    companion object {
        private lateinit var following: String
        private lateinit var follower: String
        private const val TAG = "DetailUser"
        const val EXTRA_LOGIN = "extra_login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
        )
    }
}