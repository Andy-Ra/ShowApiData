package com.andyra.submission1ivanandyramadhan

import android.content.ContentValues.TAG
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.ListData
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
import com.andyra.submission1ivanandyramadhan.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mlistp = ArrayList<ProfileData>()

    private val mlist = ArrayList<ProfileData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setTitle(R.string.slistp)


        mBinding.rvlist.setHasFixedSize(true)

        getusername()
        mlist.addAll(mlistp)
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
                        setusername(mresponse.items)
                    }
                    else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ListData>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun setusername(mitem: ArrayList<ProfileData?>?) {
        if (mitem != null) {
            for(mlogin in mitem){
                if (mlogin != null) {
                    val mdclient = ApiConfig.getApiService().getDetail(mlogin.login.toString())
                    mdclient.enqueue(object : Callback<ProfileData>{
                        override fun onResponse(
                            call: Call<ProfileData>,
                            dresponse: Response<ProfileData>
                        ) {
                            if (dresponse.isSuccessful){
                                val mdrespon = dresponse.body()
                                if (mdrespon != null){
                                            val mprofile = ProfileData(
                                                mdrespon.id,
                                                mdrespon.avatarUrl,
                                                mdrespon.name,
                                                mdrespon.login,
                                                mdrespon.location,
                                                mdrespon.company,
                                                mdrespon.followers,
                                                mdrespon.following,
                                                mdrespon.publicRepos
                                            )
                                            mlistp.add(mprofile)
                                        }
                                }
                                else{
                                    Log.e(TAG, "onFailure: ${dresponse.message()}")
                                }
                            }

                        override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                            Log.e(TAG, "onFailure: ${t.message}")
                        }
                    })
                }
            }
        }
    }


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