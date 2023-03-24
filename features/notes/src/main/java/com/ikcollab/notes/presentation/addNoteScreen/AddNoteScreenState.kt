package com.ikcollab.notes.presentation.addNoteScreen

import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.note.FolderState
import com.ikcollab.model.local.note.NoteEntity

data class AddNoteScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val note: NoteEntity? = null,
    val folder:List<FolderDto>? = null
)