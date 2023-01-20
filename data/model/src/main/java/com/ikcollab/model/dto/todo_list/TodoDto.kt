package com.ikcollab.model.dto.todo_list

data class TodoDto(
    val id: Int,
    val title: String,
    val dateCreated: Long,
    val deadline: Long,
    val priority: String,
    val repeatStatus: String,
    val categoryId: Int,
    val isCompleted: Boolean,
)