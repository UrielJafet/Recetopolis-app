package com.example.recetopolis.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Login : Screen("login")
    data object Profile : Screen("profile")
}