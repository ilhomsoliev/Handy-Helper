package com.ikcollab.local.dao.notes

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao : BaseDao<NoteEntity> {
    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteEntity?

    @Query("DELETE FROM ${NoteEntity.TABLE_NAME}")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM ${NoteEntity.TABLE_NAME} WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE ${NoteEntity.COLUMN_FOLDER_ID} = :folderId")
    fun getNotesByFolderId(folderId: Int): Flow<List<NoteEntity>>

    @Query("SELECT COUNT(*) FROM ${NoteEntity.TABLE_NAME} WHERE ${NoteEntity.COLUMN_FOLDER_ID} = :folderId")
    suspend fun countNotesOfFolder(folderId: Int): Int
}