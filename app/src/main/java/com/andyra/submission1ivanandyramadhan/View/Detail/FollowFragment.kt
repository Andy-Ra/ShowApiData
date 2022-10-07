package com.andyra.submission1ivanandyramadhan.View.Detail

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Api.ApiConfig
import com.andyra.submission1ivanandyramadhan.Data.FollowData
import com.andyra.submission1ivanandyramadhan.Data.Items
import com.andyra.submission1ivanandyramadhan.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {
    private lateinit var mBinding: FragmentFollowBinding

    private val mlistfoll = ArrayList<Items>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFollowBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        getfollow()

        mBinding.rvlistfol.setHasFixedSize(true)
    }

    private fun getfollow() {
        mlistfoll.clear()
        showItems()

        var mclient: Call<FollowData>
        val user_follow = arguments?.getString(EXTRA_USERNAME).toString()
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        if (index == 1) {
            mclient = ApiConfig.getApiService().getfollowing(user_follow)
        } else {
            mclient = ApiConfig.getApiService().getfollowers(user_follow)
        }
        mclient.enqueue(object : Callback<FollowData> {
            override fun onResponse(call: Call<FollowData>, response: Response<FollowData>) {
                if (response.isSuccessful) {
                    val mresponse = response.body()
                    if (mresponse != null) {
                        if (mresponse.size > 0) {
                            showFollow(mresponse)
                            mBinding.apply {
                                rvlistfol.visibility = View.VISIBLE
                            }
                        } else {
                            mBinding.tvnffol.visibility = View.VISIBLE
                            showLoading(false)
                        }
                    }
                } else {
                    Log.e(TAG, "${response.message()}")
                }

            }

            override fun onFailure(call: Call<FollowData>, t: Throwable) {
                Log.e(TAG, "${t.message}")
            }
        })
    }

    private fun showFollow(mresponse: FollowData) {
        for (data in mresponse) {
            val mpfoll = Items(
                data.avatarUrl,
                data.login
            )
            mlistfoll.add(mpfoll)
        }
        showRecyclerList()
    }

    private fun showRecyclerList() {
        showLoading(false)
        mBinding.apply {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvlistfol.layoutManager = GridLayoutManager(root.context, 2)
            } else {
                rvlistfol.layoutManager = LinearLayoutManager(root.context)
            }
            val mlistprofileadapter = ListProfileAdapter(mlistfoll)
            rvlistfol.adapter = mlistprofileadapter
        }
    }

    private fun showItems() {
        showLoading(true)
        mBinding.apply {
            tvnffol.visibility = View.GONE
            rvlistfol.visibility = View.GONE
        }
    }

    private fun showLoading(mload: Boolean) {
        if (mload) {
            mBinding.mpfol.visibility = View.VISIBLE
        } else {
            mBinding.mpfol.visibility = View.INVISIBLE
        }
    }

    companion object {
        private const val TAG = "FollowFragment"
        const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USERNAME = "Andy-Ra"
    }
}