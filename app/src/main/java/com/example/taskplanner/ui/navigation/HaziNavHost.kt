package com.example.taskplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskplanner.ui.home.HomeScreen
import com.example.taskplanner.ui.task.CreateTaskScreen
import com.example.taskplanner.ui.task.EditTaskScreen

@Composable
fun HaziNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TaskListDestination.route,
        modifier = modifier
    ) {
        composable(route = TaskListDestination.route) {
            HomeScreen(navigateToTaskEdit = {
                navController.navigateSingleTopTo("${EditTaskDestination.route}/${it}")
            })
        }
        composable(route = CreateTaskDestination.route) {
            CreateTaskScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = EditTaskDestination.routeWithArgs,
            arguments = listOf(navArgument(EditTaskDestination.taskIdArg) {
                type = NavType.IntType
            })
        ) {
            EditTaskScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        this@navigateSingleTopTo.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}