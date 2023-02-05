package com.ikcollab.todolist.todoCategoryScreen

import com.ikcollab.model.dto.todo_list.TodoCategoryDto

data class TodoCategoryState(
    val categories: List<TodoCategoryDto> = emptyList(),
)