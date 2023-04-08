package com.ikcollab.notes.presentation.addNoteScreen

sealed class AddNoteScreenEvent {
    data class OnLoadNote(val noteId: Int,val folderId:Int) : AddNoteScreenEvent()
    data class OnTitleChange(val value: String) : AddNoteScreenEvent()
    data class OnFolderChange(val value: Int) : AddNoteScreenEvent()
    data class OnDateChange(val value: Long) : AddNoteScreenEvent()
    data class OnDescriptionChange(val value: String) : AddNoteScreenEvent()
    object OnGoBack:AddNoteScreenEvent()
    object GetFolders:AddNoteScreenEvent()
    object InsertToDatabase : AddNoteScreenEvent()
}