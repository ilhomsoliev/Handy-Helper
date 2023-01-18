package com.ikcollab.local.di

import android.content.Context
import androidx.room.Room
import com.ikcollab.local.dao.FolderDao
import com.ikcollab.local.dao.NoteDao
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


}