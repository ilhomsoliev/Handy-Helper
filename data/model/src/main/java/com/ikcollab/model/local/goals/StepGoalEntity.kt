package com.ikcollab.model.local.goals

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = StepGoalEntity.TABLE_NAME)
data class StepGoalEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_IS_COMPLETED) val isCompleted: Boolean,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
    @ColumnInfo(name = COLUMN_DEADLINE) val deadline: Long,

) {
    companion object{
        const val TABLE_NAME = "step_goal_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IS_COMPLETED = "is_completed"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_DEADLINE = "deadline"
    }
}