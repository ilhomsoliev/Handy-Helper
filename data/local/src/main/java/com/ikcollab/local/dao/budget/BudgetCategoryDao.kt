package com.ikcollab.local.dao.budget

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.budget.BudgetCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetCategoryDao : BaseDao<BudgetCategoryEntity> {
    @Query("SELECT * FROM ${BudgetCategoryEntity.TABLE_NAME} WHERE type = :type")
    fun getBudgetCategoriesByType(type:String): Flow<List<BudgetCategoryEntity>>

    @Query("SELECT * FROM ${BudgetCategoryEntity.TABLE_NAME} WHERE id = :categoryId")
    suspend fun getBudgetCategoryById(categoryId: Int): BudgetCategoryEntity?

    @Query("DELETE FROM ${BudgetCategoryEntity.TABLE_NAME} WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Int)
}