package com.ikcollab.domain.usecase.todo_list.todo

import com.ikcollab.repository.notes.NotesRepository
import com.ikcollab.repository.todo_list.TodoRepository
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoId: Int) = repository.getTodoById(todoId)

}