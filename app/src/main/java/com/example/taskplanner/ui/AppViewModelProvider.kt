package com.example.taskplanner.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskplanner.TaskPlannerApplication
import com.example.taskplanner.ui.home.HomeViewModel
import com.example.taskplanner.ui.task.EditTaskViewModel
import com.example.taskplanner.ui.task.TaskViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TaskViewModel(taskPlannerApplication().container.tasksRepository)
        }

        initializer {
            HomeViewModel(taskPlannerApplication().container.tasksRepository)
        }

        initializer {
            EditTaskViewModel(
                this.createSavedStateHandle(),
                taskPlannerApplication().container.tasksRepository
            )
        }
    }
}

fun CreationExtras.taskPlannerApplication(): TaskPlannerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskPlannerApplication)