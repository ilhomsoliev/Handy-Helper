package com.ikcollab.todolist.todoList.todoHorizontalPageScreen

sealed class TodoHorizontalPageEvent {
    data class OnLoadPage(val id: Int) : TodoHorizontalPageEvent()
    object OpenBottomSheet : TodoHorizontalPageEvent()

}