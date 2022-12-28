package com.itis.androidtestproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun get(id: Int): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAll()

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>
}