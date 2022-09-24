package com.andyra.submission1ivanandyramadhan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
import com.andyra.submission1ivanandyramadhan.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity() {
    private lateinit var mBinding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        setTitle(R.string.detailuser)
        mBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        putprocess()
    }

    private fun putprocess() {
        showLoading(true)
        val username = intent.getStringExtra(EXTRA_LOGIN).toString()
        val mclient = ApiConfig.getApiService().getDetail(username)
        mclient.enqueue(object : Callback<ProfileData> {
            override fun onResponse(
                call: Call<ProfileData>,
                response: Response<ProfileData>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val mresponbody = response.body()
                    if (mresponbody != null) {
                        mBinding.apply {
                            if (mresponbody.location == null) {
                                tvdlocation.text = StringBuilder(" - ")
                            } else {
                                tvdlocation.text = " " + mresponbody.location
                            }

                            if (mresponbody.company == null) {
                                tvdcompany.text = StringBuilder(" - ")

                            } else {
                                tvdcompany.text = " " + mresponbody.company
                            }
                            Glide.with(root)
                                .load(mresponbody.avatarUrl)
                                .circleCrop()
                                .into(imgdetail)

                            tvdname.text = mresponbody.name
                            tvduname.text = StringBuilder("@").append(mresponbody.login)
                            tvdppeople.text =
                                " ${mresponbody.following} ${getString(R.string.following)} \t ${mresponbody.followers} ${
                                    getString(R.string.follower)
                                }"
                            btndrepo.text =
                                "${getString(R.string.repo)} (${mresponbody.publicRepos})"
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun showLoading(mload: Boolean) {
        if (mload) {
            mBinding.dprogress.visibility = View.VISIBLE
        } else {
            mBinding.dprogress.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        const val EXTRA_LOGIN = "extra_login"
    }
}