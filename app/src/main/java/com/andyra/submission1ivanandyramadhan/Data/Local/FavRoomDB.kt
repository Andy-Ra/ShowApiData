package com.andyra.submission1ivanandyramadhan.Data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavData::class], version = 1)
abstract class FavRoomDB : RoomDatabase(){
    abstract fun getFavDao(): FavDao

    companion object{

        @Volatile
        private var INSTANCE: FavRoomDB? = null
        @JvmStatic
        fun getDatabase(context: Context): FavRoomDB {
            if (INSTANCE == null) {
                synchronized(FavRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavRoomDB::class.java, "DBFavorite")
                        .build()
                }
            }
            return INSTANCE as FavRoomDB
        }
    }
}
