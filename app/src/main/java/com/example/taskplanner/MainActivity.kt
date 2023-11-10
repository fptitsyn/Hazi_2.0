package com.example.taskplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskplanner.ui.navigation.CreateTaskDestination
import com.example.taskplanner.ui.navigation.EditTaskDestination
import com.example.taskplanner.ui.navigation.HaziNavHost
import com.example.taskplanner.ui.navigation.TaskListDestination
import com.example.taskplanner.ui.navigation.haziTopBarScreens
import com.example.taskplanner.ui.navigation.navigateSingleTopTo
import com.example.taskplanner.ui.theme.TaskPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskPlannerTheme {
                val navController = rememberNavController()

                val currentBackState by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackState?.destination
                val currentScreen =
                    haziTopBarScreens.find { it.finalRoute == currentDestination?.route } ?: TaskListDestination

                val canNavigateBack = navController.previousBackStackEntry != null
                val canDeleteTask = currentScreen.finalRoute == EditTaskDestination.finalRoute

                Scaffold(
                    topBar = {
                        if (!canDeleteTask) {
                            HaziTopAppBar(
                                canNavigateBack = canNavigateBack,
                                canDeleteTask = canDeleteTask,
                                onClickBack = { navController.popBackStack() },
                                screenTitle = currentScreen.title
                            )
                        }
                    },
                    floatingActionButton = {
                        if (currentScreen.finalRoute == TaskListDestination.finalRoute) {
                            LargeFloatingActionButton(
                                onClick = { navController.navigateSingleTopTo(CreateTaskDestination.finalRoute) },
                                shape = MaterialTheme.shapes.small
                            ) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task button")
                            }
                        }
                    }
                ) { innerPadding ->
                    HaziNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
