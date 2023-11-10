package com.example.taskplanner.data

import android.content.Context

interface AppContainer {
    val tasksRepository: TasksRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        OfflineTasksRepository(TaskDatabase.getDatabase(context).taskDao())
    }
}