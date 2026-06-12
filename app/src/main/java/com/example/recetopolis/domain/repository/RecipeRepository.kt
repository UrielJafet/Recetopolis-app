package com.example.recetopolis.domain.repository

import com.example.recetopolis.domain.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
    suspend fun getRecipeById(id: String): Result<Recipe>
    suspend fun searchRecipes(query: String?, category: String?, difficulty: String?): Result<List<Recipe>>
    suspend fun createRecipe(
        title: String,
        description: String,
        ingredients: List<String>,
        steps: List<String>,
        category: String,
        difficulty: String,
        imageUrl: String?
    ): Result<Recipe>
}