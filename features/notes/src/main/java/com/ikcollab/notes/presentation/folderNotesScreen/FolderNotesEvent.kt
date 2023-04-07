package com.ikcollab.notes.presentation.folderNotesScreen


sealed class FolderNotesEvent {
    data class OnFolderIdChange(val folderId:Int) : FolderNotesEvent()
    data class OnNoteIdChange(val noteId:Int) : FolderNotesEvent()
    object GetNotesByFolderId: FolderNotesEvent()
    object GetFolderById: FolderNotesEvent()
    object DeleteNoteById: FolderNotesEvent()
    object NavigateToAddNote: FolderNotesEvent()
    object NavigateToEditNote: FolderNotesEvent()
    object NavigateToShowDetails: FolderNotesEvent()
}