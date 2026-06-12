package com.example.recetopolis.data.repository

import com.example.recetopolis.core.network.FakeApiService
import com.example.recetopolis.core.session.SessionManager
import com.example.recetopolis.data.mapper.toDomain
import com.example.recetopolis.data.remote.dto.CreateRecipeRequest
import com.example.recetopolis.domain.model.Recipe
import com.example.recetopolis.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.first

class RecipeRepositoryImpl(
    private val apiService: FakeApiService,
    private val sessionManager: SessionManager
) : RecipeRepository {

    override suspend fun getRecipes(): Result<List<Recipe>> {
        return try {
            val recipes = apiService.getRecipes().map { it.toDomain() }
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipeById(id: String): Result<Recipe> {
        return try {
            val recipe = apiService.getRecipeById(id).toDomain()
            Result.success(recipe)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchRecipes(query: String?, category: String?, difficulty: String?): Result<List<Recipe>> {
        return try {
            val recipes = apiService.searchRecipes(query, category, difficulty).map { it.toDomain() }
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createRecipe(
        title: String,
        description: String,
        ingredients: List<String>,
        steps: List<String>,
        category: String,
        difficulty: String,
        imageUrl: String?
    ): Result<Recipe> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("Not authenticated")
            val request = CreateRecipeRequest(
                title = title,
                description = description,
                ingredients = ingredients,
                steps = steps,
                category = category,
                difficulty = difficulty,
                imageUrl = imageUrl
            )
            val recipe = apiService.createRecipe(token, request).toDomain()
            Result.success(recipe)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
