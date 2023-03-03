package com.ikcollab.todolist.todoCategoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ikcollab.todolist.todoCategoryScreen.components.TodoCategoryItem

@Composable
fun TodoCategoryScreen(
    state: TodoCategoryState,
    onEvent: (TodoCategoryEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            items(state.categories) {
                TodoCategoryItem(title = it.title)
            }
        }

    }
    Box(modifier = Modifier.fillMaxSize().padding(end = 25.dp, bottom = 25.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(onClick = {
            onEvent(TodoCategoryEvent.OpenBottomSheet)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}