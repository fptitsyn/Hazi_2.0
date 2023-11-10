package com.example.taskplanner.ui.navigation


interface HaziDestination {
    val route: String
    val title: String
    val routeWithArgs: String
    val finalRoute: String
}

object TaskListDestination : HaziDestination {
    override val route = "task-list"
    override val title = "Tasks"
    override val routeWithArgs = ""
    override val finalRoute = routeWithArgs.ifBlank { route }
}

object CreateTaskDestination : HaziDestination {
    override val route = "create-task"
    override val title = "Create Task"
    override val routeWithArgs = ""
    override val finalRoute = routeWithArgs.ifBlank { route }
}

object EditTaskDestination: HaziDestination {
    override val route = "edit-task"
    override val title = "Edit Task"
    const val taskIdArg = "itemId"
    override val routeWithArgs = "$route/{$taskIdArg}"
    override val finalRoute = routeWithArgs.ifBlank { route }
}

val haziTopBarScreens = listOf(TaskListDestination, CreateTaskDestination, EditTaskDestination)