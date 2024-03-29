package com.ikcollab.todolist.todoList.todoHorizontalPageScreen

import com.ikcollab.model.dto.todo_list.TodoDto

data class TodoHorizontalPageState(
    val isLoading:Boolean = false,
    val error:String?=null,
    val todoList:List<TodoDto> = listOf(),
)