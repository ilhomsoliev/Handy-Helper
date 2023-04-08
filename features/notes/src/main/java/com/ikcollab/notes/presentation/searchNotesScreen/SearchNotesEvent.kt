package com.ikcollab.notes.presentation.searchNotesScreen

sealed class SearchNotesEvent {
    data class OnFolderIdChange(val folderId:Int) : SearchNotesEvent()
    data class OnDialogStateChange(val state:Boolean) : SearchNotesEvent()
    data class OnNoteIdChange(val noteId:Int) : SearchNotesEvent()
    data class OnSearchChange(val search:String) : SearchNotesEvent()
    object GetFolders :SearchNotesEvent()
    object GetNotes: SearchNotesEvent()
    object DeleteNoteById: SearchNotesEvent()
    object NavigateToEditNote: SearchNotesEvent()
    object NavigateToShowDetails: SearchNotesEvent()
}