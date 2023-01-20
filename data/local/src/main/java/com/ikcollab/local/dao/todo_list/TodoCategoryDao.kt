package com.ikcollab.local.dao.todo_list

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.todo_list.TodoCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoCategoryDao : BaseDao<TodoCategoryEntity> {
    @Query("SELECT * FROM ${TodoCategoryEntity.TABLE_NAME}")
    fun getTodoCategories(): Flow<List<TodoCategoryEntity>>

    @Query("SELECT * FROM ${TodoCategoryEntity.TABLE_NAME} WHERE id = :todoCategoryId")
    suspend fun getTodoCategoryById(todoCategoryId: Int): TodoCategoryEntity?

    @Query("DELETE FROM ${TodoCategoryEntity.TABLE_NAME}")
    suspend fun deleteAllTodoCategories()

    @Query("DELETE FROM ${TodoCategoryEntity.TABLE_NAME} WHERE id = :todoCategoryId")
    suspend fun deleteTodoCategoryById(todoCategoryId: Int)
}