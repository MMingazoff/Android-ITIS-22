package com.itis.androidtestproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class], version = 3)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}