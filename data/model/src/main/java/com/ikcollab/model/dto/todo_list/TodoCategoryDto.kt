package com.ikcollab.model.dto.todo_list

data class TodoCategoryDto(
    val id: Int? = null,
    val title: String,
    val dateCreated: Long,
    val todosCount:Int,
)