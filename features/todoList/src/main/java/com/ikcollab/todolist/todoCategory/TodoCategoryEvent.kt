package com.ikcollab.todolist.todoCategory

sealed class TodoCategoryEvent {
    object BackPress : TodoCategoryEvent()
    object OpenBottomSheet : TodoCategoryEvent()
    object InsertCategory : TodoCategoryEvent()

}