package com.ikcollab.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikcollab.local.dao.budget.BudgetCategoryDao
import com.ikcollab.local.dao.budget.BudgetStoryDao
import com.ikcollab.local.dao.goals.GoalDao
import com.ikcollab.local.dao.goals.StepGoalDao
import com.ikcollab.local.dao.notes.FolderDao
import com.ikcollab.local.dao.notes.NoteDao
import com.ikcollab.local.dao.todo_list.TodoCategoryDao
import com.ikcollab.local.dao.todo_list.TodoDao
import com.ikcollab.model.local.budget.BudgetCategoryEntity
import com.ikcollab.model.local.budget.BudgetStoryEntity
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity
import com.ikcollab.model.local.todo_list.TodoCategoryEntity
import com.ikcollab.model.local.todo_list.TodoEntity

@Database(
    entities = [NoteEntity::class, FolderEntity::class, StepGoalEntity::class, GoalEntity::class, TodoCategoryEntity::class, TodoEntity::class, BudgetCategoryEntity::class, BudgetStoryEntity::class],
    version = 3,
    exportSchema = false
)
abstract class HandyHelperDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
    abstract fun goalDao(): GoalDao
    abstract fun stepGoalDao(): StepGoalDao
    abstract fun todoDao(): TodoDao
    abstract fun todoCategoryDao(): TodoCategoryDao
    abstract fun budgetStoryDao(): BudgetStoryDao
    abstract fun budgetCategoryDao(): BudgetCategoryDao
}