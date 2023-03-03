package com.ikcollab.domain.usecase.todo_list.todoCategory

import com.ikcollab.model.dto.toTodoCategoryDto
import com.ikcollab.model.dto.toTodoDto
import com.ikcollab.model.dto.todo_list.TodoCategoryDto
import com.ikcollab.model.dto.todo_list.TodoDto
import com.ikcollab.repository.todo_list.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodoCategoriesUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(): Flow<List<TodoCategoryDto>> {
        return repository.getCategories().map { todos ->
            todos.sortedBy { it.dateCreated }.map {
                it.toTodoCategoryDto(todosCount = repository.getCategoryTodosCount(it.id!!))
            }
        }
    }
}