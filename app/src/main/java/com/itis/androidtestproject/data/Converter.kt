package com.itis.androidtestproject.data

import androidx.room.TypeConverter
import java.util.*


class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}