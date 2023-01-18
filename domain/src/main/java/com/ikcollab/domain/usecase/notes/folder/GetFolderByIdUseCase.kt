package com.ikcollab.domain.usecase.notes.folder

import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class GetFolderByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(folderId: Int) = repository.getFolderById(folderId)

}