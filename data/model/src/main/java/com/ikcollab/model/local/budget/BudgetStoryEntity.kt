package com.ikcollab.model.local.budget

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = BudgetStoryEntity.TABLE_NAME)
data class BudgetStoryEntity(
    @PrimaryKey(autoGenerate = true)
    
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_COMMENT) val comment: String,
    @ColumnInfo(name = COLUMN_VALUE) val value: Int,
    @ColumnInfo(name = COLUMN_TYPE) val type: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
    @ColumnInfo(name = COLUMN_CATEGORY_ID) val categoryId: Int,
) {
    companion object {
        const val TABLE_NAME = "budget_story_table"
        const val COLUMN_ID = "id"
        const val COLUMN_VALUE = "value"
        const val COLUMN_COMMENT = "comment"
        const val COLUMN_TYPE = "type"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_CATEGORY_ID = "category_id"
    }
}