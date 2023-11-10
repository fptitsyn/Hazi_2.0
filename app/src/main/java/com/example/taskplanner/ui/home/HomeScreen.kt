package com.example.taskplanner.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskplanner.R
import com.example.taskplanner.data.Task
import com.example.taskplanner.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateToTaskEdit: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        TaskList(
            taskList = homeUiState.taskList,
            onItemClick = { navigateToTaskEdit(it.id) },
            onCheckedTask = { task ->
                coroutineScope.launch {
                    viewModel.completeTask(task)
                    snackbarHostState.showSnackbar("Task completed!")
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Composable
fun TaskList(
    onItemClick: (Task) -> Unit,
    onCheckedTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
    taskList: List<Task> = List(100) { i -> Task(i, "Task # $i", "") }
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(items = taskList, key = { it.id }) { task ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(task) }
            ) {
                TaskItem(
                    taskName = task.name,
                    taskDesc = task.description,
                    onCheckedChange = { onCheckedTask(task) },
                    expandable = task.description.isNotEmpty(),
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    taskName: String,
    taskDesc: String,
    onCheckedChange: (Boolean) -> Unit,
    expandable: Boolean,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = taskName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
            if (expanded) {
                Text(
                    text = taskDesc,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp)
                )
            }
        }
        if (expandable) {
            IconButton(
                onClick = { expanded = !expanded },
            ) {
                Icon(
                    imageVector = if (expanded) {
                        Icons.Filled.ExpandLess
                    } else {
                        Icons.Filled.ExpandMore
                    },
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
        Checkbox(
            checked = false,
            onCheckedChange = onCheckedChange
        )
    }
}
