package com.ikcollab.local.di

import android.content.Context
import androidx.room.Room
import com.ikcollab.local.dao.budget.BudgetCategoryDao
import com.ikcollab.local.dao.budget.BudgetStoryDao
import com.ikcollab.local.dao.goals.GoalDao
import com.ikcollab.local.dao.goals.StepGoalDao
import com.ikcollab.local.dao.notes.FolderDao
import com.ikcollab.local.dao.notes.NoteDao
import com.ikcollab.local.dao.todo_list.TodoCategoryDao
import com.ikcollab.local.dao.todo_list.TodoDao
import com.ikcollab.local.db.HandyHelperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


private const val DB_NAME = "db_name"

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    @Named(value = DB_NAME)
    fun provideDatabaseName(): String {
        return "handy_helper_db"//BuildConfig.DB_NAME
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @Named(value = DB_NAME) dbname: String,
        @ApplicationContext context: Context
    ): HandyHelperDatabase {
        return Room.databaseBuilder(context, HandyHelperDatabase::class.java, dbname)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(appDatabase: HandyHelperDatabase): NoteDao {
        return appDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideFolderDao(appDatabase: HandyHelperDatabase): FolderDao {
        return appDatabase.folderDao()
    }

    @Provides
    @Singleton
    fun provideGoalDao(appDatabase: HandyHelperDatabase): GoalDao {
        return appDatabase.goalDao()
    }

    @Provides
    @Singleton
    fun provideStepGoalDao(appDatabase: HandyHelperDatabase): StepGoalDao {
        return appDatabase.stepGoalDao()
    }


    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: HandyHelperDatabase): TodoDao {
        return appDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoCategoryDao(appDatabase: HandyHelperDatabase): TodoCategoryDao {
        return appDatabase.todoCategoryDao()
    }

    @Provides
    @Singleton
    fun provideBudgetStoryDao(appDatabase: HandyHelperDatabase): BudgetStoryDao {
        return appDatabase.budgetStoryDao()
    }

    @Provides
    @Singleton
    fun provideBudgetCategoryDao(appDatabase: HandyHelperDatabase): BudgetCategoryDao {
        return appDatabase.budgetCategoryDao()
    }


}