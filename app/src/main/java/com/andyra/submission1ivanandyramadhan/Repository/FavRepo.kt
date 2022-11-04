package com.andyra.submission1ivanandyramadhan.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.andyra.submission1ivanandyramadhan.Data.Local.FavDao
import com.andyra.submission1ivanandyramadhan.Data.Local.FavData
import com.andyra.submission1ivanandyramadhan.Data.Local.FavRoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepo (mApplication: Application) {
    private val mFavDao: FavDao
    private val mExecutorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavRoomDB.getDatabase(mApplication)
        mFavDao = db.getFavDao()
    }

    fun getAllFav(): LiveData<List<FavData>> = mFavDao.getListFav()
    fun checkUserFav(mLogin: String) : List<FavData> = mFavDao.getCheckFav(mLogin)

        fun insertFav(mFavData: FavData) {
        mExecutorService.execute { mFavDao.insUserFav(mFavData) }
    }
    fun deleteFav(mLogin: String) {
        mExecutorService.execute{
            mFavDao.dltUserFav(mLogin)
        }
    }
}