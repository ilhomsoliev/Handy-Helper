package com.ikcollab.domain.usecase.todo_list.todoCategory

import com.ikcollab.local.dao.todo_list.TodoCategoryDao
import com.ikcollab.model.dto.toTodoCategoryEntity
import com.ikcollab.model.dto.toTodoEntity
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto
import com.ikcollab.repository.todo_list.TodoRepository
import javax.inject.Inject

class DeleteTodoCategoryByIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoCategoryDto: TodoCategoryDto) {
        repository.deleteTodoCategory(todoCategoryDto.toTodoCategoryEntity())
    }
}