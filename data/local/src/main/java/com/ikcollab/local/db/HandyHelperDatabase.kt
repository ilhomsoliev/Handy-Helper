package com.ikcollab.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikcollab.local.dao.FolderDao
import com.ikcollab.local.dao.NoteDao
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity

@Database(
    entities = [NoteEntity::class,FolderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HandyHelperDatabase:RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
}