package com.ikcollab.model.local.note

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FolderEntity.TABLE_NAME)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)

    
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
) {
    companion object{
        const val TABLE_NAME = "folder_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DATE_CREATED = "date_created"
    }
}