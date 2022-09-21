package com.andyra.submission1ivanandyramadhan

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.ListData
import com.andyra.submission1ivanandyramadhan.Data.ListProfile
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
import com.andyra.submission1ivanandyramadhan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private val mlist = ArrayList<ProfileData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setTitle(R.string.slistp)


        mBinding.rvlist.setHasFixedSize(true)

        getusername()
        mlist.addAll(listprofiles)
        showRecyclerList()
    }

    private fun getusername() {
        val mclient = ApiConfig.getApiService().getSearch(EXTRA_SEARCH)
        mclient.enqueue(object : Callback<ListData>{
            override fun onResponse(
                call: Call<ListData>,
                response: Response<ListData>
            ) {
                if(response.isSuccessful){
                    val mresponse = response.body()
                    if (mresponse != null){
                        setusername(mresponse.item)
                    }
                    else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ListData>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message()}")
            }

        })
    }

    private fun setusername(item: ProfileData) {
        val listname = ArrayList<String>()
        for(i in item){
            muser.add(i.login)
        }
    }

//    private val listprofiles: ArrayList<ProfileData>
//        get() {

//            val dataphoto = resources.obtainTypedArray(R.array.avatar)
//            val dataname = resources.getStringArray(R.array.name)
//            val datausername = resources.getStringArray(R.array.username)
//            val datalocation = resources.getStringArray(R.array.location)
//            val datacompany = resources.getStringArray(R.array.company)
//            val datafollower = resources.getStringArray(R.array.followers)
//            val datafollowing = resources.getStringArray(R.array.following)
//            val datarepo = resources.getStringArray(R.array.repository)
//
//            val mlistp = ArrayList<ListProfile>()
//            for(i in dataname.indices){
//                val mprofile = ListProfile(dataphoto.getResourceId(i, -1)
//                                , dataname[i], datausername[i], datalocation[i]
//                                , datacompany[i], datafollower[i], datafollowing[i], datarepo[i])
//                mlistp.add(mprofile)
//            }
//        };


    private fun showRecyclerList() {
        mBinding.apply {
            if(application.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvlist.layoutManager = GridLayoutManager(root.context, 2)
            }
            else{
                rvlist.layoutManager = LinearLayoutManager(root.context)
            }
            val mlistprofileadapter = ListProfileAdapter(mlist)
            rvlist.adapter = mlistprofileadapter
        }
    }

    companion object{
        private const val TAG = "MainActivity"
        const val EXTRA_SEARCH = "Andy"
    }
}