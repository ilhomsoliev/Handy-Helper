package com.ikcollab.repository.notes

import androidx.annotation.VisibleForTesting
import com.ikcollab.local.dao.FolderDao
import com.ikcollab.local.dao.NoteDao
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity
import javax.inject.Inject

class NotesRepository @Inject constructor(
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val noteDao:NoteDao,
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val folderDao: FolderDao,
) {

    suspend fun insertNote(noteEntity: NoteEntity) = noteDao.insert(noteEntity)
    suspend fun insertFolder(folderEntity: FolderEntity) = folderDao.insert(folderEntity)
    suspend fun deleteNote(noteEntity: NoteEntity) = noteDao.delete(noteEntity)
    suspend fun deleteFolder(folderEntity: FolderEntity) = folderDao.delete(folderEntity)
    suspend fun deleteNoteById(noteId: Int) = noteDao.deleteNoteById(noteId)
    suspend fun deleteFolderById(folderId: Int) = folderDao.deleteFolderById(folderId)
    suspend fun getNoteById(noteId: Int):NoteEntity? = noteDao.getNoteById(noteId)
    suspend fun getFolderById(noteId: Int):NoteEntity? = noteDao.getNoteById(noteId)
    suspend fun getNoteSByFolderId(folderId: Int): List<NoteEntity> = noteDao.getNotesByFolderId(folderId)
}