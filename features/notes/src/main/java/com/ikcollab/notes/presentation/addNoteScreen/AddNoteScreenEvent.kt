package com.ikcollab.notes.presentation.addNoteScreen

sealed class AddNoteScreenEvent {
    data class OnLoadNote(val noteId: Int) : AddNoteScreenEvent()
    data class OnTitleChange(val value: String) : AddNoteScreenEvent()
    data class OnDescriptionChange(val value: String) : AddNoteScreenEvent()
    object OnGoBack:AddNoteScreenEvent()
    object InsertToDatabase : AddNoteScreenEvent()
}