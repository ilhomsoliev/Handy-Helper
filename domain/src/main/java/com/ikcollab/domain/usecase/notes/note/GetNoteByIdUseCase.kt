package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) = repository.getNoteById(noteId)

}