package com.ikcollab.todolist.todoListScreen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import com.ikcollab.todolist.components.TodoTabLayout
import com.ikcollab.todolist.todoListScreen.todoHorizontalPageScreen.TodoHorizontalPage
import com.ikcollab.todolist.todoListScreen.todoHorizontalPageScreen.TodoHorizontalPageEvent
import com.ikcollab.todolist.todoListScreen.todoHorizontalPageScreen.TodoHorizontalPageViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TodoListScreen(
    state: TodoListState,
    onEvent: (TodoListEvent) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = state.categories.size)
    LaunchedEffect(key1 = state.currentPage, block = {
        pagerState.scrollToPage(state.currentPage)
    })
    LaunchedEffect(key1 = pagerState.currentPage, block = {
        onEvent(TodoListEvent.ChangePage(pagerState.currentPage))
        pagerState.scrollToPage(state.currentPage)
    })

    if (state.categories.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TodoTabLayout(state.categories.map { it.title }, state.currentPage, onPageChange = {
                onEvent(TodoListEvent.ChangePage(it))
            })
            HorizontalPager(
                state = pagerState,
                count = state.categories.map { it.title }.size
            ) { index ->
                val viewModel = hiltViewModel<TodoHorizontalPageViewModel>()
                viewModel.onEvent(TodoHorizontalPageEvent.OnLoadPage(index))
                onEvent(TodoListEvent.OnTaskCategoryIdChange(index))
                TodoHorizontalPage(
                    state = viewModel.state.collectAsState().value,
                    onEvent = { event ->
                        when (event) {
                            is TodoHorizontalPageEvent.OpenBottomSheet -> {
                                onEvent(TodoListEvent.OpenBottomSheet)
                            }
                            else -> viewModel.onEvent(event)
                        }
                    })
            }
        }
    }
}