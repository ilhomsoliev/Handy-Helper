package com.ikcollab.domain.usecase.notes.folder

import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.toFolderEntity
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class DeleteFolderByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(folderId: Int) {
        repository.deleteFolderById(folderId)
    }
}