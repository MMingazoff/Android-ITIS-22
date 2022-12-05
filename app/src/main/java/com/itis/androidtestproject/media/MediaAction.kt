package com.itis.androidtestproject.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MediaAction: Parcelable {
    PREV, PLAY, PAUSE, NEXT, STOP
}