package com.ikcollab.repository.budget

import com.ikcollab.local.dao.budget.BudgetCategoryDao
import com.ikcollab.local.dao.budget.BudgetStoryDao
import com.ikcollab.model.local.budget.BudgetCategoryEntity
import com.ikcollab.model.local.budget.BudgetStoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val budgetStoryDao: BudgetStoryDao,
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val budgetCategoryDao: BudgetCategoryDao,
) {

    suspend fun insertBudgetStory(budgetStoryEntity: BudgetStoryEntity) =
        budgetStoryDao.insert(budgetStoryEntity)

    suspend fun insertBudgetCategory(budgetCategoryEntity: BudgetCategoryEntity) =
        budgetCategoryDao.insert(budgetCategoryEntity)

    suspend fun deleteBudgetStory(budgetStoryEntity: BudgetStoryEntity) =
        budgetStoryDao.delete(budgetStoryEntity)

    suspend fun deleteBudgetCategory(budgetCategoryEntity: BudgetCategoryEntity) =
        budgetCategoryDao.delete(budgetCategoryEntity)

   /* suspend fun deleteAllStoriesByType(type: String) =
        budgetCategoryDao.deleteAllBudgetStoriesByType(budgetCategoryEntity)
*/
    suspend fun deleteStoryById(storyId: Int) = budgetStoryDao.deleteStoryById(storyId)
    suspend fun deleteStoriesByType(type: String) = budgetStoryDao.deleteStoriesByType(type)
    suspend fun deleteCategoryById(categoryId: Int) =
        budgetCategoryDao.deleteCategoryById(categoryId)

    suspend fun getStoryById(storyId: Int): BudgetStoryEntity? =
        budgetStoryDao.getBudgetStoryById(storyId)

    suspend fun getCategoryById(categoryId: Int): BudgetCategoryEntity? =
        budgetCategoryDao.getBudgetCategoryById(categoryId)

    fun getCategoriesByType(type: String): Flow<List<BudgetCategoryEntity>> =
        budgetCategoryDao.getBudgetCategoriesByType(type)

    fun getStoriesByCategoryId(categoryId: Int): Flow<List<BudgetStoryEntity>> =
        budgetStoryDao.getBudgetStoriesByCategoryId(categoryId)

    fun getStoriesByType(type: String): Flow<List<BudgetStoryEntity>> =
        budgetStoryDao.getBudgetStoriesByType(type)

    fun getStorySumByCategoryId(id: Int): Flow<Double?> =
        budgetStoryDao.getSumByCategoryId(id)

    suspend fun deleteAllBudgetByCategoryId(categoryId: Int) = budgetStoryDao.deleteAllBudgetByCategoryId(categoryId)

    fun getStorySumByType(type: String): Flow<Double?> = budgetStoryDao.getSumByType(type)
    //fun getFolders(): Flow<List<FolderEntity>> = budgetCategoryDao.getFolders()
}