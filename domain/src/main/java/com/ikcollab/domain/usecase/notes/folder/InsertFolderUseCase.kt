package com.ikcollab.domain.usecase.notes.folder

import com.ikcollab.model.dao.note.FolderDto
import com.ikcollab.model.dao.toFolderEntity
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(folderDto: FolderDto) {
        repository.insertFolder(folderDto.toFolderEntity())
    }
}