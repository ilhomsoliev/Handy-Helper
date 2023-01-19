package com.ikcollab.model.dao.note

data class FolderDto(
    val id:Int,
    val name:String,
    val dateCreated: Long,
){
    //fun init():FolderDto =
}
data class FolderDtoState(
    val folders:List<FolderDto> = emptyList()
)

