package com.rnm.data.di

import com.rnm.data.remote.repository.CharactersRepositoryImpl
import com.rnm.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryBindingModule {

    @Binds
    @Singleton
    abstract fun bindCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository
}