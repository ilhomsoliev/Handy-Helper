package com.ikcollab.domain.usecase.todo_list.todoCategory

import com.ikcollab.repository.todo_list.TodoRepository
import javax.inject.Inject

class GetTodoCategoryByIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoCategoryId: Int) = repository.getTodoCategoryById(todoCategoryId)

}