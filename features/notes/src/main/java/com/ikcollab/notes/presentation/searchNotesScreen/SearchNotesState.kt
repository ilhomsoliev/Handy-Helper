package com.ikcollab.notes.presentation.searchNotesScreen

import com.ikcollab.model.dto.note.NoteDto

data class SearchNotesState(
    val notes:List<NoteDto> = listOf(),

)