package com.ikcollab.model.local.todo_list

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TodoEntity.TABLE_NAME)
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
    @ColumnInfo(name = COLUMN_DEADLINE) val deadline: Long,
    @ColumnInfo(name = COLUMN_PRIORITY) val priority: String,
    @ColumnInfo(name = COLUMN_REPEAT_STATUS) val repeatStatus: String,
    @ColumnInfo(name = COLUMN_CATEGORY_ID) val categoryId: Int,
    @ColumnInfo(name = COLUMN_IS_COMPLETED) val isCompleted: Boolean,
) {
    companion object {
        const val TABLE_NAME = "todo_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_DEADLINE = "deadline"
        const val COLUMN_PRIORITY = "priority"
        const val COLUMN_REPEAT_STATUS = "repeat_status"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_IS_COMPLETED = "is_completed"
    }
}