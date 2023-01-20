package com.ikcollab.domain.usecase.todo_list.todo

import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.toNoteEntity
import com.ikcollab.model.dto.toTodoEntity
import com.ikcollab.model.dto.todo_list.TodoDto
import com.ikcollab.repository.notes.NotesRepository
import com.ikcollab.repository.todo_list.TodoRepository
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoDto: TodoDto) {
        repository.insertTodo(todoDto.toTodoEntity())
    }
}