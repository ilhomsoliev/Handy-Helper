package com.ikcollab.domain.usecase.notes.note

import com.ikcollab.repository.notes.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountNotesOfFolderUseCase @Inject constructor(
    private val repository: NotesRepository
){
    suspend fun invoke(folderId:Int): Int {
        return repository.countNotesOfFolder(folderId = folderId)
    }
}