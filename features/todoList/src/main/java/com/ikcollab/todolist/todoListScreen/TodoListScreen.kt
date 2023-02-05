package com.ikcollab.todolist.todoListScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.*
import com.ikcollab.todolist.components.TodoTabContent
import com.ikcollab.todolist.components.TodoTabLayout

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TodoListScreen(
    state: TodoListState,
    onEvent: (TodoListEvent) -> Unit
) {
    var tabPage by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = state.categories.size)
    LaunchedEffect(key1 = tabPage, block = {
        pagerState.scrollToPage(tabPage)
    })
    LaunchedEffect(key1 = pagerState.currentPage, block = {
        tabPage = pagerState.currentPage
        pagerState.scrollToPage(tabPage)
    })
    if (state.categories.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TodoTabLayout(state.categories.map { it.title }, tabPage, onPageChange = {
                tabPage = it
            })
            TodoTabContent(state.categories.map { it.title }, pagerState, onPageChange = {
                tabPage = it
            })
        }
    }
}