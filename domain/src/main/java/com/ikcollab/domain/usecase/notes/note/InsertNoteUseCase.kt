package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.model.dao.note.NoteDto
import com.ikcollab.model.dao.toNoteEntity
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteDto: NoteDto) {
        repository.insertNote(noteDto.toNoteEntity())
    }
}