package com.example.todolist.di

import com.example.todolist.models.DataBaseRepository
import com.example.todolist.models.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDataBaseRepository(
        dataBaseRepository: DataBaseRepository
    ): Repository

}