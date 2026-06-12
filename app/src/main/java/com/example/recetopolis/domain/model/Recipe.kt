package com.example.recetopolis.domain.model

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val category: String,
    val difficulty: String,
    val imageUrl: String?,
    val authorName: String,
    val rating: Float,
    val createdAt: String
)