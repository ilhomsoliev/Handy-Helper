package com.ikcollab.local.dao.budget

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.budget.BudgetStoryEntity
import com.ikcollab.model.local.todo_list.TodoCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetStoryDao : BaseDao<BudgetStoryEntity> {
    @Query("SELECT * FROM ${BudgetStoryEntity.TABLE_NAME} WHERE type = :type")
    fun getBudgetStoriesByType(type: String): Flow<List<BudgetStoryEntity>>

    @Query("SELECT * FROM ${BudgetStoryEntity.TABLE_NAME} WHERE category_id = :categoryId")
    fun getBudgetStoriesByCategoryId(categoryId: Int): Flow<List<BudgetStoryEntity>>

    @Query("SELECT SUM(value) FROM ${BudgetStoryEntity.TABLE_NAME} WHERE type = :type")
    fun getSumByType(type: String): Flow<Double?>

    @Query("SELECT SUM(value) FROM ${BudgetStoryEntity.TABLE_NAME} WHERE category_id = :id")
    fun getSumByCategoryId(id: Int): Flow<Double?>

    @Query("SELECT * FROM ${BudgetStoryEntity.TABLE_NAME} WHERE id = :storyId")
    suspend fun getBudgetStoryById(storyId: Int): BudgetStoryEntity?

    @Query("DELETE FROM ${BudgetStoryEntity.TABLE_NAME} WHERE type = :type")
    suspend fun deleteAllStoriesByType(type: String)

    @Query("DELETE FROM ${BudgetStoryEntity.TABLE_NAME} WHERE type = :type")
    suspend fun deleteAllBudgetStoriesByType(type: String)

    @Query("DELETE FROM ${BudgetStoryEntity.TABLE_NAME} WHERE id = :storyId")
    suspend fun deleteStoryById(storyId: Int)
}