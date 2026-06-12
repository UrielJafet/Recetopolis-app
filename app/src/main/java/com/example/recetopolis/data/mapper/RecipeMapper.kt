package com.example.recetopolis.data.mapper

import com.example.recetopolis.data.remote.dto.RecipeDto
import com.example.recetopolis.domain.model.Recipe

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        ingredients = ingredients,
        steps = steps,
        category = category,
        difficulty = difficulty,
        imageUrl = imageUrl,
        authorName = authorId?.username ?: "Anónimo",
        rating = ratings?.map { it.rating }?.average()?.toFloat() ?: 0f,
        createdAt = createdAt
    )
}