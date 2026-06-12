package com.example.recetopolis.presentation.navigation

sealed class Routes (val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object Search : Routes("search")
    object Favorites : Routes("favorites")
    object Profile : Routes("profile")
    object EditProfile : Routes("edit_profile")
    object RecipeDetail : Routes("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe_detail/$recipeId"
    }
}