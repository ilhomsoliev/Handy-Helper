package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.toNoteEntity
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class DeleteNoteByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId:Int) {
        repository.deleteNoteById(noteId)
    }
}