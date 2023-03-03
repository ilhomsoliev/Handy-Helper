package com.ikcollab.todolist.todoListScreen.todoHorizontalPageScreen

sealed class TodoHorizontalPageEvent {
    data class OnLoadPage(val id: Int) : TodoHorizontalPageEvent()
    object OpenBottomSheet : TodoHorizontalPageEvent()

}