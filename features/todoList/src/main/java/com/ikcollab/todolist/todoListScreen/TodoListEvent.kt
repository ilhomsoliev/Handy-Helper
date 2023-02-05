package com.ikcollab.todolist.todoListScreen

import com.ikcollab.todolist.todoCategoryScreen.TodoCategoryEvent

sealed class TodoListEvent {

    object OpenCategoryScreen : TodoListEvent()
    object OpenBottomSheet : TodoListEvent()
    object InsertCategory : TodoListEvent()
    object ClearTodoList : TodoListEvent()
}