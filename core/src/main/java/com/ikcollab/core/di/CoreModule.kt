package com.ikcollab.core.di

import android.content.Context
import com.ikcollab.core.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = DataStoreManager(context)
}