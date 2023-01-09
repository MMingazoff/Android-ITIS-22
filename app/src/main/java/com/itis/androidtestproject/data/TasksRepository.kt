package com.itis.androidtestproject.data

import android.content.Context
import androidx.room.Room


class TasksRepository(context: Context): TaskDao {
    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    private val taskDao by lazy {
        db.getTaskDao()
    }

    override fun add(task: Task) = taskDao.add(task)

    override fun delete(task: Task) = taskDao.delete(task)

    override fun deleteAll() = taskDao.deleteAll()

    override fun update(task: Task) = taskDao.update(task)

    override fun get(id: Int): Task? = taskDao.get(id)

    override fun getAll(): List<Task> = taskDao.getAll()

    companion object {
        private const val DATABASE_NAME = "todo_app"
    }
}