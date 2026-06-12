package com.example.recetopolis.di

import com.example.recetopolis.core.network.FakeApiService
import com.example.recetopolis.core.session.SessionManager
import com.example.recetopolis.data.repository.AuthRepositoryImpl
import com.example.recetopolis.data.repository.FavoriteRepositoryImpl
import com.example.recetopolis.data.repository.RecipeRepositoryImpl
import com.example.recetopolis.data.repository.UserRepositoryImpl
import com.example.recetopolis.domain.repository.AuthRepository
import com.example.recetopolis.domain.repository.FavoriteRepository
import com.example.recetopolis.domain.repository.RecipeRepository
import com.example.recetopolis.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: FakeApiService,
        sessionManager: SessionManager
    ): AuthRepository = AuthRepositoryImpl(apiService, sessionManager)

    @Provides
    @Singleton
    fun provideRecipeRepository(
        apiService: FakeApiService,
        sessionManager: SessionManager
    ): RecipeRepository = RecipeRepositoryImpl(apiService, sessionManager)

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        apiService: FakeApiService,
        sessionManager: SessionManager
    ): FavoriteRepository = FavoriteRepositoryImpl(apiService, sessionManager)

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: FakeApiService,
        sessionManager: SessionManager
    ): UserRepository = UserRepositoryImpl(apiService, sessionManager)
}