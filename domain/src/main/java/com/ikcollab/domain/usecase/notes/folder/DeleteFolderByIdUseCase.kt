package com.ikcollab.domain.usecase.notes.folder

import com.ikcollab.model.dao.note.FolderDto
import com.ikcollab.model.dao.note.NoteDto
import com.ikcollab.model.dao.note.toFolderEntity
import com.ikcollab.model.dao.note.toNoteEntity
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class DeleteFolderByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(folderDto: FolderDto) {
        repository.deleteFolder(folderDto.toFolderEntity())
    }
}