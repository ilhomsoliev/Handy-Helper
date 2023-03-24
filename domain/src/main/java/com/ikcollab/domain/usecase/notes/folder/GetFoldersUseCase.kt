package com.ikcollab.domain.usecase.notes.folder

import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.toFolderDto
import com.ikcollab.repository.notes.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(): Flow<List<FolderDto>> {
        return repository.getFolders().map { folders ->
            folders.sortedBy { it.id }.map { it.toFolderDto(
                notesCount = repository.countNotesOfFolder(folderId = it.id!!)
            ) }
        }
    }
}