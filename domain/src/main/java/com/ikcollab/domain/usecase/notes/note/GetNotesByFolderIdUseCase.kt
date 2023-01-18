package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.model.dao.note.NoteDto
import com.ikcollab.model.dao.note.toNoteDto
import com.ikcollab.repository.notes.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesByFolderIdUseCase @Inject constructor(
    private val repository: NotesRepository
){
    suspend operator fun invoke(folderId:Int): Flow<List<NoteDto>> {
        return repository.getNoteSByFolderId(folderId = folderId).map { notes ->
            notes.sortedBy { it.dateCreated }.map { it.toNoteDto() }
        }
    }
}