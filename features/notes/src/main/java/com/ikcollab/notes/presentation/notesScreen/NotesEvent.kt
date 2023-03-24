package com.ikcollab.notes.presentation.notesScreen


sealed class NotesEvent {
    object GetNotesByFolderId :NotesEvent()
    object DeleteAllNotesByFolderId:NotesEvent()
    object GetFolders:NotesEvent()
    object DeleteFolder:NotesEvent()
    object DeleteNoteById:NotesEvent()
    data class OnFolderIdChange(val value: Int):NotesEvent()
    data class OnNoteIdChange(val value: Int):NotesEvent()
    object NavigateToAddNote: NotesEvent()
    object NavigateToEditFolder: NotesEvent()
    object NavigateToFoldersDetails: NotesEvent()
    object NavigateToShowNotesDetails: NotesEvent()
}