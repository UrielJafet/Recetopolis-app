package com.example.recetopolis.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recetopolis.presentation.auth.login.LoginScreen
import com.example.recetopolis.presentation.auth.register.RegisterScreen
import com.example.recetopolis.presentation.favorites.FavoritesScreen
import com.example.recetopolis.presentation.profiles.ProfileScreen
import com.example.recetopolis.presentation.home.HomeScreen
import com.example.recetopolis.presentation.recipes.RecipeDetailScreen

@Composable
fun AppNavGraph(
    startDestination: String = Routes.Login.route  // ← Empieza en Login
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Routes.Register.route) },
                onNavigateToHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigateUp() },
                onNavigateToHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(
                navController = navController,
                onNavigateToRecipeDetail = { recipeId ->
                    navController.navigate(Routes.RecipeDetail.createRoute(recipeId))
                }
            )
        }

        composable(Routes.RecipeDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
            RecipeDetailScreen(
                recipeId = recipeId,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                navController = navController,
                onNavigateToLogin = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Favorites.route) {
            FavoritesScreen(
                navController = navController,
                onNavigateToRecipeDetail = { recipeId ->
                    navController.navigate(Routes.RecipeDetail.createRoute(recipeId))
                }
            )
        }
    }
}