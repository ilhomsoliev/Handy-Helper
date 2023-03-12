package com.ikcollab.notes.presentation.foldersNotesScreen

import com.ikcollab.model.dto.note.NoteState

data class FoldersNotesScreenState (
    val isLoading: Boolean = false,
    val error: String = "",
    var note: NoteState? = null,
    val folderId:Int = -1,
    val noteId:Int = -1
)