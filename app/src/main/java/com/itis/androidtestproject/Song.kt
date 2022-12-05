package com.itis.androidtestproject

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Int,
    val artist: String,
    val title: String,
    @RawRes val raw: Int,
    @DrawableRes val cover: Int
) : Parcelable