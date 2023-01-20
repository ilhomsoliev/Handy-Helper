package com.ikcollab.model.local.todo_list

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ikcollab.model.local.note.NoteEntity

@Entity(tableName = TodoCategoryEntity.TABLE_NAME)
data class TodoCategoryEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
) {
    companion object{
        const val TABLE_NAME = "todo_category_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DATE_CREATED = "date_created"
    }
}