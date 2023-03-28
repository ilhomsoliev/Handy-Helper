package com.ikcollab.todolist.todoList

sealed class TodoListEvent {
    data class ChangePage(val page:Int) : TodoListEvent()

    object OpenCategoryScreen : TodoListEvent()
    object OpenBottomSheet : TodoListEvent()
    object InsertCategory : TodoListEvent()
    object ClearTodoList : TodoListEvent()
    data class OnTaskCategoryIdChange(val value: Int) : TodoListEvent()

}