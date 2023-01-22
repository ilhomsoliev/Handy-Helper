package com.ikcollab.model.local.budget

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ikcollab.model.local.goals.GoalEntity

@Entity(tableName = BudgetCategoryEntity.TABLE_NAME)
data class BudgetCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_DATE_CREATED) val dateCreated: Long,
    @ColumnInfo(name = COLUMN_TYPE) val type: String,
) {
    companion object{
        const val TABLE_NAME = "budget_category_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_TYPE = "type"
    }
}