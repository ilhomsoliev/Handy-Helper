package com.ikcollab.notes.presentation.addNoteScreen

import com.ikcollab.model.local.note.NoteEntity

data class AddNoteScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val note: NoteEntity? = null,
)