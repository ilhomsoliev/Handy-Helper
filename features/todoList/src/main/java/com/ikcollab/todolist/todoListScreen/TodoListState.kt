package com.ikcollab.todolist.todoListScreen

import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto

data class TodoListState(
    val categories: List<TodoCategoryDto> = emptyList(),
    val activeCategory: List<TodoDto> = emptyList(),
)