package com.example.taskplanner.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskplanner.R
import com.example.taskplanner.data.Task
import com.example.taskplanner.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun CreateTaskScreen(
    navigateBack: () -> Unit,
    viewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TaskInputForm(
            taskDetails = viewModel.taskUiState.taskDetails,
            onValueChange = viewModel::updateUiState
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.saveTask()
                    navigateBack()
                }
            },
            enabled = viewModel.taskUiState.isEntryValid,
            modifier = Modifier
                .padding(top = 40.dp)
        ) {
            Text(text = stringResource(R.string.create))
        }
    }
}

@Composable
fun TaskInputForm(
    taskDetails: Task,
    modifier: Modifier = Modifier,
    onValueChange: (Task) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        TextField(
            value = taskDetails.name,
            onValueChange = { onValueChange(taskDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.task_name)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = taskDetails.description,
            onValueChange = { onValueChange(taskDetails.copy(description = it)) },
            label = { Text(stringResource(R.string.task_description)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
