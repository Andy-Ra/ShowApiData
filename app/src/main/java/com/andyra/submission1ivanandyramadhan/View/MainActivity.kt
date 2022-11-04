package com.andyra.submission1ivanandyramadhan.View

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.Local.ThemePreference
import com.andyra.submission1ivanandyramadhan.Data.Remote.Items
import com.andyra.submission1ivanandyramadhan.Data.Remote.ListProfile
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.ViewModel.ThemeVMFactory
import com.andyra.submission1ivanandyramadhan.ViewModel.ThemeViewModel
import com.andyra.submission1ivanandyramadhan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val Context.mDataStore: DataStore<Preferences> by preferencesDataStore(R.string.theme_key.toString())
    private val mListProf = ArrayList<Items>()
    private var username: String = defUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setTitle(R.string.slistp)

        showLoading(true)
        getUsername()
        mBinding.rvlist.setHasFixedSize(true)
    }

    override fun onPrepareOptionsMenu(mMenu: Menu): Boolean {
        val pref = ThemePreference.getInstance(mDataStore)
        val mViewModels = ViewModelProvider(this, ThemeVMFactory(pref))[ThemeViewModel::class.java]

        mViewModels.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mMenu.findItem(R.id.darkMode).isVisible = false
                mMenu.findItem(R.id.lightMode).isVisible = true
                mViewModels.saveThemeSetting(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mMenu.findItem(R.id.darkMode).isVisible = true
                mMenu.findItem(R.id.lightMode).isVisible = false
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(mMenu: Menu): Boolean {
        val mMenuInflate = menuInflater
        mMenuInflate.inflate(R.menu.search_bar, mMenu)
        mMenuInflate.inflate(R.menu.fav_bar, mMenu)
        mMenuInflate.inflate(R.menu.darkmode_bar, mMenu)

        val mSearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val mSearchView = mMenu.findItem(R.id.searchuser).actionView as SearchView

        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(componentName))
        mSearchView.queryHint = resources.getString(R.string.search)
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                username = query
                getUsername()

                mSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = ThemePreference.getInstance(mDataStore)
        val mViewModels = ViewModelProvider(this, ThemeVMFactory(pref))[ThemeViewModel::class.java]

        when (item.itemId) {
            R.id.favUser -> {
                val moving = Intent(this, FavActivity::class.java)
                startActivity(moving)
                return true
            }
            R.id.darkMode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mViewModels.saveThemeSetting(true)
                return true
            }
            R.id.lightMode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mViewModels.saveThemeSetting(false)
                return true
            }
            else -> return true
        }
    }


    private fun getUsername() {
        showItems()
        val mclient = ApiConfig.getApiService().getSearch(username)
        mclient.enqueue(object : Callback<ListProfile> {
            override fun onResponse(
                call: Call<ListProfile>,
                response: Response<ListProfile>
            ) {
                if (response.isSuccessful) {
                    val mresponse = response.body()
                    if (mresponse != null) {
                        if (mresponse.totalCount > 0) {
                            setusername(mresponse.items)
                            mBinding.apply {
                                rvlist.visibility = View.VISIBLE
                            }
                        } else {
                            mBinding.tvamnf.visibility = View.VISIBLE
                            showLoading(false)
                        }
                    } else {
                        Log.e(TAG, response.message().toString())
                    }

                }
            }

            override fun onFailure(call: Call<ListProfile>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    private fun setusername(mitems: ArrayList<Items>) {
        mListProf.clear()
        for (mitem in mitems) {
            val mpprofile = Items(
                mitem.avatarUrl,
                mitem.login
            )
            mListProf.add(mpprofile)
        }
        showRecyclerList()
    }

    private fun showRecyclerList() {
        showLoading(false)
        mBinding.apply {
            rvlist.layoutManager = LinearLayoutManager(root.context)
            val mListProfileAdapter = ListProfileAdapter(mListProf)
            rvlist.adapter = mListProfileAdapter
        }
    }

    private fun showItems() {
        mBinding.apply {
            tvamnf.visibility = View.GONE
            rvlist.visibility = View.GONE
        }
    }

    private fun showLoading(mLoad: Boolean){
        mBinding.mainprogress.isVisible = mLoad
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val defUser = "Andy-Ra"
    }
}