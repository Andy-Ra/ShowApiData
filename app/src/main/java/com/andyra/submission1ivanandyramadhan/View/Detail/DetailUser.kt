package com.andyra.submission1ivanandyramadhan.View.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.andyra.submission1ivanandyramadhan.Adapter.FragmentFollowAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.Remote.ProfileData
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.View.FavActivity
import com.andyra.submission1ivanandyramadhan.ViewModel.FavVMFactory
import com.andyra.submission1ivanandyramadhan.ViewModel.FavViewModel
import com.andyra.submission1ivanandyramadhan.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity() {
    private lateinit var mBinding: ActivityDetailUserBinding
    var dUsername: String = ""
    var dAvatar: String = ""
    var arraytab: Array<String> = emptyArray()
    var isFav: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.detailuser)
        mBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        showLoading(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getUsername()
        putProcess()
    }


    private fun putProcess() {
        val mclient = ApiConfig.getApiService().getDetail(dUsername)
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
                            dAvatar = StringBuilder(mresponbody.avatarUrl).toString()
                        }
                        arraytab = arrayOf(
                            "${getString(R.string.following)} (${mresponbody.following})",
                            "${getString(R.string.follower)} (${mresponbody.followers})"
                        )
                    } else {
                        Log.e(TAG, response.message().toString())
                    }
                    showFollower()
                    getFavManager()
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    private fun showFollower() {
        val mFollowAdapter = FragmentFollowAdapter(this)
        mFollowAdapter.followUser = dUsername
        mBinding.apply {
            vpfollow.adapter = mFollowAdapter
            TabLayoutMediator(tabsfollow, vpfollow) { mtab, mpos ->
                mtab.text = arraytab[mpos]
            }.attach()
        }
        supportActionBar?.elevation = 0f
    }

    private fun getFavManager() {
        Log.e(TAG, "ara ${dUsername}")
        val mFavViewModel = obtainViewModel(this@DetailUser)
        isFav = false
        CoroutineScope(Dispatchers.IO).launch {
            val mCheckUser = mFavViewModel.checkFavVM(dUsername)
            withContext(Dispatchers.Main) {
                if (mCheckUser != null){
                    if(mCheckUser.count() > 0) {
                        Log.e(TAG, "ara isi ${mCheckUser}")
                        mBinding.tbFavManager.isChecked = true
                        isFav = true
                    }
                    else {
                        mBinding.tbFavManager.isChecked = false
                        isFav = false
                    }
                }
            }
        }

        mBinding.tbFavManager.setOnClickListener {
            if (!isFav) {
                mFavViewModel.insertFavVM(dAvatar, dUsername)
                Toast.makeText(this, getString(R.string.add_fav_user), Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                mFavViewModel.deleteFavVM(dUsername)
                Toast.makeText(this, getString(R.string.delete_from_fav), Toast.LENGTH_SHORT)
                    .show()
            }
            mBinding.tbFavManager.isChecked 
            getFavManager()
        }
    }

    private fun getUsername() {
        dUsername = intent.getStringExtra(EXTRA_LOGIN).toString()
    }

    private fun showLoading(mload: Boolean) {
        if (mload) {
            mBinding.pgdetail.visibility = View.VISIBLE
        } else {
            mBinding.pgdetail.visibility = View.INVISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) this.finish()
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(mFavActivity: DetailUser): FavViewModel {
        val factory = FavVMFactory.getInstance(mFavActivity.application)
        return ViewModelProvider(mFavActivity, factory)[FavViewModel::class.java]
    }

    companion object {
        private const val TAG = "DetailUser"
        const val EXTRA_LOGIN = "extra_login"
    }
}