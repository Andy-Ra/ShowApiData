package com.andyra.submission1ivanandyramadhan.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andyra.submission1ivanandyramadhan.Data.Local.FavData
import com.andyra.submission1ivanandyramadhan.Repository.FavRepo
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : ViewModel() {
    private val mFavRepo: FavRepo = FavRepo(application)
    fun getAllFav(): LiveData<List<FavData>> = mFavRepo.getAllFav()

    fun insertFavVM(mAvatar: String,  mLogin: String){
        val muser = FavData(mAvatar, mLogin)
        mFavRepo.insertFav(muser)
    }

    fun deleteFavVM(mLogin: String){
        mFavRepo.deleteFav(mLogin)
    }

    fun checkFavVM(mLogin:String) :List<FavData> = mFavRepo.checkUserFav(mLogin)
}