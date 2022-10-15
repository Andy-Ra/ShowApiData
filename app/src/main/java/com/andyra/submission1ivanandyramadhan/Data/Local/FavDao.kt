package com.andyra.submission1ivanandyramadhan.Data.Local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavDao {
    @Query("SELECT * from FavData ORDER BY login ASC")
    fun getListFav(): LiveData<List<FavData>>

    @Query("SELECT * FROM FavData WHERE login = :login")
    fun getCheckFav(login: String): LiveData<List<FavData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insUserFav(mFavData: FavData)

    @Delete
    fun dltUserFav(login: String)

}