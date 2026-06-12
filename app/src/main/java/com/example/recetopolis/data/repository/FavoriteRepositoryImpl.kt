package com.example.recetopolis.data.repository

import com.example.recetopolis.core.network.FakeApiService
import com.example.recetopolis.core.session.SessionManager
import com.example.recetopolis.data.mapper.toDomain
import com.example.recetopolis.domain.model.Recipe
import com.example.recetopolis.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val apiService: FakeApiService,
    private val sessionManager: SessionManager
) : FavoriteRepository {

    override suspend fun getFavorites(): Result<List<Recipe>> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("No autenticado")
            val favorites = apiService.getFavorites(token).map { it.toDomain() }
            Result.success(favorites)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavorite(recipeId: String): Result<Unit> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("No autenticado")
            apiService.addFavorite(token, recipeId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFavorite(recipeId: String): Result<Unit> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("No autenticado")
            apiService.removeFavorite(token, recipeId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isFavorite(recipeId: String): Result<Boolean> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("No autenticado")
            val favorites = apiService.getFavorites(token)
            Result.success(favorites.any { it.id == recipeId })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}