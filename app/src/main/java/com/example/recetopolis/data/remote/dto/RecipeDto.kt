package com.example.recetopolis.data.remote.dto

data class RecipeDto(
    val id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val category: String,
    val difficulty: String,
    val imageUrl: String?,
    val authorId: UserDto?,
    val ratings: List<RatingDto>?,
    val createdAt: String
)

data class RatingDto(
    val userId: String,
    val rating: Int,
    val comment: String
)

data class CreateRecipeRequest(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val category: String,
    val difficulty: String,
    val imageUrl: String? = null
)

data class CategoryDto(
    val id: String,
    val name: String,
    val icon: String? = null
)