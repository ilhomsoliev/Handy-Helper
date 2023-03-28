package com.ikcollab.handyhelper.app.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ikcollab.handyhelper.app.navigation.Screens
import com.ikcollab.todolist.todoCategory.TodoCategoryEvent
import com.ikcollab.todolist.todoCategory.TodoCategoryScreen
import com.ikcollab.todolist.todoCategory.TodoCategoryScreenViewModel
import com.ikcollab.todolist.todoList.TodoListEvent
import com.ikcollab.todolist.todoList.TodoListScreen
import com.ikcollab.todolist.todoList.TodoListViewModel

fun NavGraphBuilder.TodoListNavGraph(navController: NavController) {
    navigation(route = Graph.TodoListGraph.route, startDestination = Screens.TodoListScreen.route) {
        composable(route = Screens.TodoListScreen.route) {
            val viewModel = hiltViewModel<TodoListViewModel>()
            TodoListScreen(viewModel.state.collectAsState().value, onEvent = { event ->
                when (event) {
                    is TodoListEvent.OpenBottomSheet -> {
                       /* coroutineScope.launch {
                            modalSheetState.show()
                        }*/
                    }
                    is TodoListEvent.OnTaskCategoryIdChange -> {
                        //onEvent(NavigationEvent.OnTodoCategoryIdChange(event.value))
                    }
                    else -> viewModel.onEvent(event)
                }
            })
        }
        composable(route = Screens.TodoCategoryScreen.route) {
            val viewModel = hiltViewModel<TodoCategoryScreenViewModel>()
            TodoCategoryScreen(
                viewModel.state.collectAsState().value,
                onEvent = { event ->
                    when (event) {
                        is TodoCategoryEvent.OpenBottomSheet -> {
                           /* coroutineScope.launch {
                                modalSheetState.show()
                            }*/
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}