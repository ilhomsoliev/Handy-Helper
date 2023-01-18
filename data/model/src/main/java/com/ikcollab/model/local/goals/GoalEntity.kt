package com.ikcollab.model.local.goals

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity

@Entity(tableName = GoalEntity.TABLE_NAME)
data class GoalEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
    @ColumnInfo(name = COLUMN_DATE_START) val dateStart: Long,
    @ColumnInfo(name = COLUMN_DATE_END) val dateEnd: Long,
) {
    companion object{
        const val TABLE_NAME = "goal_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_DATE_START = "date_start"
        const val COLUMN_DATE_END = "date_end"
    }
}