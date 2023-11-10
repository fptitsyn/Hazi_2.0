package com.example.taskplanner.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.Task
import com.example.taskplanner.data.TasksRepository
import com.example.taskplanner.ui.navigation.EditTaskDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle[EditTaskDestination.taskIdArg])

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    init {
        viewModelScope.launch {
            taskUiState = tasksRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState()
        }
    }

    private fun validateInput(uiState: Task = taskUiState.taskDetails) : Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    fun updateUiState(taskDetails: Task) {
        taskUiState = TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    suspend fun updateTask() {
        if (validateInput(taskUiState.taskDetails)) {
            tasksRepository.updateTask(taskUiState.taskDetails)
        }
    }

    suspend fun deleteTask() {
        tasksRepository.deleteTask(taskUiState.taskDetails)
    }
}
