package com.example.taskplanner.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskplanner.HaziTopAppBar
import com.example.taskplanner.R
import com.example.taskplanner.ui.AppViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditTaskScreen(
    navigateBack: () -> Unit,
    canNavigateBack: Boolean = true,
    canDeleteTask: Boolean = true,
    canSaveTask: Boolean = true,
    viewModel: EditTaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.taskUiState
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HaziTopAppBar(
                canNavigateBack = canNavigateBack,
                canDeleteTask = canDeleteTask,
                canSaveTask = canSaveTask,
                screenTitle = stringResource(R.string.edit_task_capitalized),
                onClickBack = navigateBack,
                onClickDelete = {
                    deleteConfirmationRequired = true
                },
                onClickSave = {
                    coroutineScope.launch {
                        viewModel.updateTask()
                        navigateBack()
                    }
                }
            )
        }
    ) { innerPadding ->
        EditTaskBody(
            uiState = uiState,
            viewModel = viewModel,
            coroutineScope = coroutineScope,
            navigateBack = navigateBack,
            modifier = Modifier.padding(innerPadding)
        )

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteTask()
                        navigateBack()
                    }
                },
                onDeleteCancel = {
                    deleteConfirmationRequired = false
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun EditTaskBody(
    uiState: TaskUiState,
    viewModel: EditTaskViewModel,
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TaskInputForm(
            taskDetails = uiState.taskDetails,
            onValueChange = viewModel::updateUiState
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.updateTask()
                    navigateBack()
                }
            },
            enabled = uiState.isEntryValid,
            modifier = Modifier
                .padding(top = 40.dp)
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = stringResource(R.string.delete_confirmation_title)) },
        text = { Text(text = stringResource(R.string.delete_confirmation_text)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}