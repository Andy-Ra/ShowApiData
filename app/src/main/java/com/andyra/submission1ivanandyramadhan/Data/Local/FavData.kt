package com.andyra.submission1ivanandyramadhan.Data.Local

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Entity
@Parcelize
data class FavData(
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login: String,
) : Parcelable