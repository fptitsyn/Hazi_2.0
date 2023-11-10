package com.example.taskplanner.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.taskplanner.data.Task
import com.example.taskplanner.data.TasksRepository

class TaskViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    fun updateUiState(taskDetails: Task) {
        taskUiState = TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    private fun validateInput(uiState: Task = taskUiState.taskDetails) : Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun saveTask() {
        if (validateInput()) {
            tasksRepository.insertTask(taskUiState.taskDetails)
        }
    }
}

data class TaskUiState(
    val taskDetails: Task = Task(),
    val isEntryValid: Boolean = false
)

fun Task.toTaskUiState(isEntryValid: Boolean = false) : TaskUiState = TaskUiState(
    taskDetails = this,
    isEntryValid = isEntryValid
)