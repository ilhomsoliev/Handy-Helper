package com.ikcollab.todolist.todoCategory

import com.ikcollab.model.dto.todo_list.TodoCategoryDto

data class TodoCategoryState(
    val categories: List<TodoCategoryDto> = emptyList(),
)