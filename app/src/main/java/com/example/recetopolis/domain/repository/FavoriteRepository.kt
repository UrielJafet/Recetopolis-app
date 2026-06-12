package com.example.recetopolis.domain.repository

import com.example.recetopolis.domain.model.Recipe

interface FavoriteRepository {
    suspend fun getFavorites(): Result<List<Recipe>>
    suspend fun addFavorite(recipeId: String): Result<Unit>
    suspend fun removeFavorite(recipeId: String): Result<Unit>
    suspend fun isFavorite(recipeId: String): Result<Boolean>
}