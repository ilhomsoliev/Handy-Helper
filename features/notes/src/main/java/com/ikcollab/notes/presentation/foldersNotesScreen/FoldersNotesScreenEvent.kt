package com.ikcollab.notes.presentation.foldersNotesScreen

import com.ikcollab.model.dto.note.NoteState
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreenEvent


sealed class FoldersNotesScreenEvent {
    data class OnFolderIdChange(val folderId:Int) : FoldersNotesScreenEvent()
    data class OnNoteIdChange(val noteId:Int) : FoldersNotesScreenEvent()
    object GetNotesByFolderId: FoldersNotesScreenEvent()
    object DeleteNoteById: FoldersNotesScreenEvent()
    object NavigateToAddNote: FoldersNotesScreenEvent()
    object NavigateToEditNote: FoldersNotesScreenEvent()
    object NavigateToShowDetails: FoldersNotesScreenEvent()
}