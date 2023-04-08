package com.ikcollab.notes.presentation.folderNotesScreen

import com.ikcollab.model.dto.note.NoteDto

data class FolderNotesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val notes: List<NoteDto> = emptyList(),
    val folderId: Int = -1,
    val noteId: Int = -1,
    var isDialogState:Boolean = false
)