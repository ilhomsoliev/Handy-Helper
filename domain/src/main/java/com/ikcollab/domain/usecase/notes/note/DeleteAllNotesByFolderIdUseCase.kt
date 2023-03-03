package com.ikcollab.domain.usecase.notes.note

import android.util.Log
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class DeleteAllNotesByFolderIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(folderId:Int) {
        Log.e("deleteNotesByFolderId","Success")
        repository.deleteAllNotesByFolderId(folderId)
    }
}