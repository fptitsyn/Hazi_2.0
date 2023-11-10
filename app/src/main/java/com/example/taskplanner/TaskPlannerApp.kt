package com.example.taskplanner

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskplanner.ui.navigation.HaziNavHost

@Composable
fun TaskPlannerApp(navController: NavHostController = rememberNavController()) {
    HaziNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HaziTopAppBar(
    canNavigateBack: Boolean = true,
    canDeleteTask: Boolean = false,
    canSaveTask: Boolean = false,
    onClickBack: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickSave: () -> Unit = {},
    screenTitle: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            }
        },
        actions = {
            if (canDeleteTask) {
                IconButton(onClick = onClickDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_task)
                    )
                }
            }
//            if (canSaveTask) {
//                IconButton(onClick = onClickSave) {
//                    Icon(
//                        imageVector = Icons.Filled.Done,
//                        contentDescription = stringResource(R.string.save_task)
//                    )
//                }
//            }
        },
        title = {
            Text(text = screenTitle)
        }
    )
}