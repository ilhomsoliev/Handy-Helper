package com.ikcollab.local.dao.notes

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao : BaseDao<FolderEntity> {
    @Query("SELECT * FROM ${FolderEntity.TABLE_NAME}")
    fun getFolders(): Flow<List<FolderEntity>>

    @Query("SELECT * FROM ${FolderEntity.TABLE_NAME} WHERE id = :folderId")
    suspend fun getFolderById(folderId: Int): FolderEntity?

    @Query("DELETE FROM ${FolderEntity.TABLE_NAME}")
    suspend fun deleteAllFolders()

    @Query("DELETE FROM ${FolderEntity.TABLE_NAME} WHERE id = :folderId")
    suspend fun deleteFolderById(folderId: Int)
}