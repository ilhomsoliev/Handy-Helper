package com.ikcollab.model.dao.note

import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity

fun NoteEntity.toNoteDto() = NoteDto(
    id = id,
    title = title,
    description = description,
    dateCreated = dateCreated,
    folderId = folderId,
)

fun NoteDto.toNoteEntity() = NoteEntity(
    id = id,
    title = title,
    description = description,
    dateCreated = dateCreated,
    folderId = folderId,
)

fun FolderEntity.toFolderDto() = FolderDto(
    id = id,
    name = name,
    dateCreated = dateCreated,
)

fun FolderDto.toFolderEntity() = FolderEntity(
    id = id,
    name = name,
    dateCreated = dateCreated,
)
