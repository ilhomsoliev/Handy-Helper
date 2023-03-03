package com.ikcollab.todolist.todoCategoryScreen

sealed class TodoCategoryEvent {
    object BackPress : TodoCategoryEvent()
    object OpenBottomSheet : TodoCategoryEvent()
    object InsertCategory : TodoCategoryEvent()

}