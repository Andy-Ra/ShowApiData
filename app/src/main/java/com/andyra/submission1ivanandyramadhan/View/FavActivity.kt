package com.andyra.submission1ivanandyramadhan.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andyra.submission1ivanandyramadhan.Adapter.ListProfileAdapter
import com.andyra.submission1ivanandyramadhan.Data.Local.FavData
import com.andyra.submission1ivanandyramadhan.Data.Remote.Items
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.ViewModel.FavViewModel
import com.andyra.submission1ivanandyramadhan.ViewModel.FavVMFactory
import com.andyra.submission1ivanandyramadhan.databinding.ActivityFavBinding

class FavActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityFavBinding
    private val mListFav = ArrayList<Items>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setTitle(R.string.title_activity_fav)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getDataFav()
    }


    private fun getDataFav() {
        showMaterial()
        
        val mFavViewModel = obtainViewModel(this@FavActivity)
        mFavViewModel.getAllFav().observe(this) { mData ->
            if (mData != null) {
                mBinding.rvlistfav.visibility = View.VISIBLE
                setDAtaFav(mData)
            }
        }
    }

    private fun obtainViewModel(mFavActivity: FavActivity): FavViewModel{
        val factory = FavVMFactory.getInstance(mFavActivity.application)
        return ViewModelProvider(mFavActivity, factory)[FavViewModel::class.java]
    }

    private fun setDAtaFav(mapData: List<FavData>) {
        mListFav.clear()
        for (data in mapData) {
            val mFoll = Items(
                data.avatarUrl,
                data.login
            )
            mListFav.add(mFoll)
        }
        showRecyclerList()
    }

    private fun showRecyclerList() {
        showLoading(false)
        mBinding.apply {
            rvlistfav.layoutManager = LinearLayoutManager(root.context)
            val mListProfileAdapter = ListProfileAdapter(mListFav)
            rvlistfav.adapter = mListProfileAdapter
        }
    }

    private fun showMaterial() {
        showLoading(true)
        mBinding.apply {
            tvnffav.visibility = View.GONE
            rvlistfav.visibility = View.GONE
        }
    }

    private fun showLoading(mload: Boolean){
        if (mload) {
            mBinding.mpfav.visibility = View.VISIBLE
        } else {
            mBinding.mpfav.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "FavActivity"
    }
}