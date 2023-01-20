package com.ikcollab.model.dto.note


data class NoteDto(
    val id:Int,
    val title:String,
    val description:String,
    val dateCreated:Long,
    val folderId:Int,
) {
    //fun init():NoteDto =
}