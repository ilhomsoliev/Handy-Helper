package com.ikcollab.repository.todo_list

import com.ikcollab.local.dao.todo_list.TodoCategoryDao
import com.ikcollab.local.dao.todo_list.TodoDao
import com.ikcollab.model.local.todo_list.TodoCategoryEntity
import com.ikcollab.model.local.todo_list.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val todoDao: TodoDao,
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val todoCategoryDao: TodoCategoryDao,
) {

    suspend fun insertTodo(todoEntity: TodoEntity) = todoDao.insert(todoEntity)
    suspend fun insertTodoCategory(todoCategoryEntity: TodoCategoryEntity) =
        todoCategoryDao.insert(todoCategoryEntity)

    suspend fun deleteTodo(todoEntity: TodoEntity) = todoDao.delete(todoEntity)
    suspend fun deleteTodoCategory(todoCategoryEntity: TodoCategoryEntity) =
        todoCategoryDao.delete(todoCategoryEntity)

    suspend fun deleteTodoById(todoId: Int) = todoDao.deleteTodoById(todoId)
    suspend fun deleteTodoCategoryById(todoCategoryId: Int) =
        todoCategoryDao.deleteTodoCategoryById(todoCategoryId)

    suspend fun getTodoById(todoId: Int): TodoEntity? = todoDao.getTodoById(todoId)
    suspend fun getTodoCategoryById(todoCategoryId: Int): TodoCategoryEntity? =
        todoCategoryDao.getTodoCategoryById(todoCategoryId)

    fun getTodosSByTodoCategoryId(categoryId: Int): Flow<List<TodoEntity>> =
        todoDao.getTodosByCategoryId(categoryId)

    fun getCategories(): Flow<List<TodoCategoryEntity>> = todoCategoryDao.getTodoCategories()
    suspend fun getCategoryTodosCount(categoryId:Int) = todoDao.getCategoryTodosCount(categoryId)
}