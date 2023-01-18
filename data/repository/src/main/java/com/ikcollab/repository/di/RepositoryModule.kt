package com.ikcollab.repository.di

import android.annotation.SuppressLint
import com.ikcollab.local.dao.FolderDao
import com.ikcollab.local.dao.NoteDao
import com.ikcollab.repository.notes.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideCharacterRepository(
        noteDao: NoteDao,
        folderDao: FolderDao,
    ) = NotesRepository(noteDao, folderDao)

}