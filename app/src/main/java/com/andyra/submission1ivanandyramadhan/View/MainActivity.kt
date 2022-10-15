package com.andyra.submission1ivanandyramadhan.View

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.Remote.Items
import com.andyra.submission1ivanandyramadhan.Data.Remote.ListProfile
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mListProf = ArrayList<Items>()

    private var username: String = defuser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setTitle(R.string.slistp)

        showLoading(true)
        getUsername()
        mBinding.rvlist.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(mmenu: Menu): Boolean {
        val menuinflate = menuInflater
        menuinflate.inflate(R.menu.search_bar, mmenu)
        menuinflate.inflate(R.menu.fav_bar, mmenu)
        menuinflate.inflate(R.menu.darkmode_bar, mmenu)

        val mSearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val mSearchView = mmenu.findItem(R.id.searchuser).actionView as SearchView

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
        when (item.itemId) {
            R.id.favuser -> {
                val moving = Intent(this, FavActivity::class.java)
                startActivity(moving)
                return true
            }
            R.id.darkmode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                return true
            }
            R.id.lightmode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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
                        if (mresponse.total_count > 0) {
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
            if (application.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvlist.layoutManager = GridLayoutManager(root.context, 2)
            } else {
                rvlist.layoutManager = LinearLayoutManager(root.context)
            }
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

    private fun showLoading(mload: Boolean) {
        if (mload) {
            mBinding.mainprogress.visibility = View.VISIBLE
        } else {
            mBinding.mainprogress.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val defuser = "Andy-Ra"
    }
}