package com.ikcollab.domain.usecase.todo_list.todo

import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.toNoteDto
import com.ikcollab.model.dto.toTodoDto
import com.ikcollab.model.dto.todo_list.TodoDto
import com.ikcollab.repository.notes.NotesRepository
import com.ikcollab.repository.todo_list.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodosByCategoryIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<TodoDto>> {
        return repository.getTodosSByTodoCategoryId(categoryId = categoryId).map { todos ->
            todos.sortedBy { it.dateCreated }.map { it.toTodoDto() }
        }
    }
}