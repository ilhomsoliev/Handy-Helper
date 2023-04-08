package com.ikcollab.notes.presentation.searchNotesScreen

import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.note.NoteDto

data class SearchNotesState(
    val notes:List<NoteDto> = listOf(),
    val folders: List<FolderDto> = emptyList(),
    val folderId: Int = -1,
    val noteId: Int = -1,
    val search:String = "",
    var isDialogState:Boolean = false
)