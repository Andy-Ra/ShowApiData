package com.andyra.submission1ivanandyramadhan.View.Detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat.setCompoundDrawableTintList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.andyra.submission1ivanandyramadhan.Adapter.FragmentFollowAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.Local.ThemePreference
import com.andyra.submission1ivanandyramadhan.Data.Remote.ProfileData
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.View.FavActivity
import com.andyra.submission1ivanandyramadhan.ViewModel.FavVMFactory
import com.andyra.submission1ivanandyramadhan.ViewModel.FavViewModel
import com.andyra.submission1ivanandyramadhan.ViewModel.ThemeVMFactory
import com.andyra.submission1ivanandyramadhan.ViewModel.ThemeViewModel
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


class DetailUserActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDetailUserBinding
    private val Context.mDataStore: DataStore<Preferences> by preferencesDataStore(R.string.theme_key.toString())

    private var dUsername: String = ""
    private var dAvatar: String = ""
    private var arraytab: Array<String> = emptyArray()
    private var isFav: Boolean = false

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
                call: Call<ProfileData>, response: Response<ProfileData>
            ) {
                if (response.isSuccessful) {
                    val mresponbody = response.body()
                    if (mresponbody != null) {
                        showLoading(false)
                        mBinding.apply {
                            dAvatar = StringBuilder(mresponbody.avatarUrl).toString()
                            tvdlocation.text = StringBuilder(" - ")
                            tvdcompany.text = StringBuilder(" - ")

                            if (mresponbody.location != null) {
                                tvdlocation.text = StringBuilder(" ").append(mresponbody.location)
                            }

                            if (mresponbody.company != null) {
                                tvdcompany.text = StringBuilder(" ").append(mresponbody.company)
                            }

                            Glide.with(applicationContext).load(dAvatar).circleCrop()
                                .into(imgdetail)

                            tvdname.text = mresponbody.name
                            tvduname.text = StringBuilder("@").append(mresponbody.login)
                            tvdrepo.text =
                                StringBuilder(" ${mresponbody.publicRepos.toString()} ").append(
                                    getString(R.string.repo)
                                )



                        }
                        arraytab = arrayOf(
                            StringBuilder(getString(R.string.following)).append("(${mresponbody.following.toString()})")
                                .toString(),
                            StringBuilder(getString(R.string.follower)).append("(${mresponbody.followers.toString()})")
                                .toString()
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
        val mFavViewModel = obtainViewModel(this@DetailUserActivity)
        isFav = false
        CoroutineScope(Dispatchers.IO).launch {
            val mCheckUser = mFavViewModel.checkFavVM(dUsername)
            withContext(Dispatchers.Main) {
                mBinding.apply {
                    if (mCheckUser.isNotEmpty()) {
                        tbFavManager.isChecked = true
                        tbFavManager.setBackgroundResource(R.drawable.ic_twotone_favorite_24)
                        isFav = true
                    } else {
                        tbFavManager.isChecked = false
                        isFav = false
                        tbFavManager.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                    imageTheme()
                }
            }
        }

        mBinding.tbFavManager.setOnClickListener {
            if (!isFav) {
                mFavViewModel.insertFavVM(dAvatar, dUsername)
                Toast.makeText(this, getString(R.string.add_fav_user), Toast.LENGTH_SHORT).show()
            } else {
                mFavViewModel.deleteFavVM(dUsername)
                Toast.makeText(this, getString(R.string.delete_from_fav), Toast.LENGTH_SHORT).show()
            }
            mBinding.tbFavManager.isChecked
            getFavManager()
        }
    }

    private fun getUsername() {
        dUsername = intent.getStringExtra(EXTRA_LOGIN).toString()
    }

    private fun showLoading(mLoad: Boolean) {
        mBinding.pgdetail.isVisible = mLoad
    }

    private fun imageTheme() {
        val pref = ThemePreference.getInstance(mDataStore)
        val mViewModels = ViewModelProvider(this, ThemeVMFactory(pref))[ThemeViewModel::class.java]
        var color = ContextCompat.getColor(mBinding.tvdlocation.context, R.color.black)
        var colorfav = ContextCompat.getColor(mBinding.tvdlocation.context, R.color.red)

        mViewModels.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                color = ContextCompat.getColor(mBinding.tvdlocation.context, R.color.white)
                colorfav = color
            }
            mBinding.apply {
                val colorList = ColorStateList.valueOf(color)
                val btnDrawable = tbFavManager.background
                val btnDrawablee = DrawableCompat.wrap(btnDrawable)

                setCompoundDrawableTintList(tvdlocation, colorList)
                setCompoundDrawableTintList(tvdcompany, colorList)
                setCompoundDrawableTintList(tvdrepo, colorList)
                DrawableCompat.setTint(btnDrawablee, colorfav)
                tbFavManager.background = btnDrawablee
            }
        }

    }

    override fun onCreateOptionsMenu(mMenu: Menu): Boolean {
        val mMenuInflate = menuInflater
        mMenuInflate.inflate(R.menu.fav_bar, mMenu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) this.finish()
        else if (item.itemId == R.id.favUser) {
            val moving = Intent(this, FavActivity::class.java)
            startActivity(moving)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(mFavActivity: DetailUserActivity): FavViewModel {
        val factory = FavVMFactory.getInstance(mFavActivity.application)
        return ViewModelProvider(mFavActivity, factory)[FavViewModel::class.java]
    }

    companion object {
        private const val TAG = "DetailUserActivity"
        const val EXTRA_LOGIN = "extra_login"
    }
}