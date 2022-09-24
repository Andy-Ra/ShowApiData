package com.andyra.submission1ivanandyramadhan

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.Items
import com.andyra.submission1ivanandyramadhan.Data.ListProfile
import com.andyra.submission1ivanandyramadhan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mlistprof = ArrayList<Items>()

    private var username: String = "Andy-Ra"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setTitle(R.string.slistp)

        showLoading(true)
        getusername()
        mBinding.rvlist.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(mmenu: Menu): Boolean {
        val menuinflate = menuInflater
        menuinflate.inflate(R.menu.option_menu, mmenu)
        val msearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val msearchView = mmenu.findItem(R.id.searchuser).actionView as SearchView

        msearchView.setSearchableInfo(msearchManager.getSearchableInfo(componentName))
        msearchView.queryHint = resources.getString(R.string.search)
        msearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                username = query
                getusername()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }


    private fun getusername() {
        val mclient = ApiConfig.getApiService().getSearch(username)
        mclient.enqueue(object : Callback<ListProfile> {
            override fun onResponse(
                call: Call<ListProfile>,
                response: Response<ListProfile>
            ) {
                if (response.isSuccessful) {
                    val mresponse = response.body()
                    if (mresponse != null) {
                        setusername(mresponse.items)
                    } else {
                        Log.e(TAG, "${response.message()}")
                    }

                }
            }
            override fun onFailure(call: Call<ListProfile>, t: Throwable) {
                Log.e(TAG, "${t.message}")
            }
        })
    }

    private fun setusername(mitems: ArrayList<Items>) {
        mlistprof.clear()
        for (mitem in mitems) {
            val mpprofile = Items(
                mitem.avatarUrl,
                mitem.login
            )
            mlistprof.add(mpprofile)
        }
        Log.e(TAG, "mlistprof : ${mlistprof}")
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
            val mlistprofileadapter = ListProfileAdapter(mlistprof)
            rvlist.adapter = mlistprofileadapter
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
    }
}