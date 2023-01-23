package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.toFolderDto
import com.ikcollab.model.dto.toNoteDto
import com.ikcollab.repository.notes.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(): Flow<List<NoteDto>> {
        return repository.getNotes().map { notes ->
            notes.sortedBy { it.dateCreated }.map { it.toNoteDto() }
        }
    }
}