package com.example.taskplanner

import android.app.Application
import com.example.taskplanner.data.AppContainer
import com.example.taskplanner.data.AppDataContainer

class TaskPlannerApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}