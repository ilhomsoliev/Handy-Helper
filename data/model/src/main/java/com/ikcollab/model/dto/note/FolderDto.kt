package com.ikcollab.model.dto.note

data class FolderDto(
    val id: Int? = null,
    val name:String,
    val dateCreated: Long,
    var countOfNotes:Int = 0
){
    //fun init():FolderDto =
}
