package com.ikcollab.notes.presentation.notesScreen

import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.note.NoteDto

data class NotesState (
    val isLoading: Boolean = false,
    val error: String = "",
    val notes: List<NoteDto> = emptyList(),
    val folders: List<FolderDto> = emptyList(),
    val folderId:Int = -1,
    val noteId:Int = -1
    )