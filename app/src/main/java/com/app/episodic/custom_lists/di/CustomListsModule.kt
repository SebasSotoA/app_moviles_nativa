package com.app.episodic.custom_lists.di

import com.app.episodic.custom_lists.data.repository_impl.CustomListsRepositoryImpl
import com.app.episodic.custom_lists.domain.repository.CustomListsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CustomListsModule {
    
    @Binds
    @Singleton
    abstract fun bindCustomListsRepository(
        customListsRepositoryImpl: CustomListsRepositoryImpl
    ): CustomListsRepository
}
