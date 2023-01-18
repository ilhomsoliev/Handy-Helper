package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.model.dao.note.NoteDto
import com.ikcollab.model.dao.note.toNoteDto
import com.ikcollab.model.dao.note.toNoteEntity
import com.ikcollab.repository.notes.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteDto: NoteDto) {
        repository.insertNote(noteDto.toNoteEntity())
    }
}