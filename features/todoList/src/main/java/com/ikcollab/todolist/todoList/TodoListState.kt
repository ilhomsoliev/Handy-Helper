package com.ikcollab.todolist.todoList

import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto

data class TodoListState(
    val categories: List<TodoCategoryDto> = emptyList(),
    //val activeCategory: List<TodoDto> = emptyList(),
    val currentPage: Int = 0,
    val activeTodos: List<TodoDto> = emptyList(),
)