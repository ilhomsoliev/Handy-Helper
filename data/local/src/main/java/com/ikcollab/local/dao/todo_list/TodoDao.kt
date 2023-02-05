package com.ikcollab.local.dao.todo_list

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.todo_list.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao : BaseDao<TodoEntity> {
    @Query("SELECT * FROM ${TodoEntity.TABLE_NAME} WHERE category_id = :categoryId")
    fun getTodosByCategoryId(categoryId:Int): Flow<List<TodoEntity>>

    @Query("SELECT * FROM ${TodoEntity.TABLE_NAME} WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int): TodoEntity?

    @Query("DELETE FROM ${TodoEntity.TABLE_NAME}")
    suspend fun deleteAllTodo()

    @Query("DELETE FROM ${TodoEntity.TABLE_NAME} WHERE id = :todoId")
    suspend fun deleteTodoById(todoId: Int)

    @Query("SELECT COUNT(*) FROM ${TodoEntity.TABLE_NAME} WHERE category_id = :categoryId")
    suspend fun getCategoryTodosCount(categoryId: Int):Int
}