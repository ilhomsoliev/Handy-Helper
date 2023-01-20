package com.ikcollab.model.local.note

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = NoteEntity.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)

    
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION) val description: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
    @ColumnInfo(name = COLUMN_FOLDER_ID) val folderId: Int,
) {
    companion object{
        const val TABLE_NAME = "note_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_FOLDER_ID = "folder_id"
    }
}