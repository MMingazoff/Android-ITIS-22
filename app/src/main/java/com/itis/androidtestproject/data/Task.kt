package com.itis.androidtestproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val longitude: Double? = null,
    val latitude: Double? = null,
    @TypeConverters(Converter::class) val date: Date? = null
)