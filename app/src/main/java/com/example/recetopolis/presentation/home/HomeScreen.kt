package com.example.recetopolis.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recetopolis.presentation.components.SearchBar
import com.example.recetopolis.presentation.home.components.CategoryFilter
import com.example.recetopolis.presentation.home.components.HomeHeader
import com.example.recetopolis.presentation.home.components.RecipeSection
import com.example.recetopolis.presentation.navigation.BottomBar

@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToRecipeDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(navController) }
    ) { padding ->

        HomeContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            uiState = uiState,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onCategorySelected = viewModel::onCategorySelected,
            onRecipeClick = onNavigateToRecipeDetail
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onRecipeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            HomeHeader()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            CategoryFilter(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = onCategorySelected
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
            } else if (uiState.recipes.isEmpty()) {
                Text(
                    text = "No se encontraron recetas",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            } else {
                // Todas las recetas populares
                RecipeSection(
                    title = if (uiState.selectedCategory == "Todas") "Populares" else uiState.selectedCategory,
                    recipes = uiState.recipes,
                    onRecipeClick = onRecipeClick
                )
            }
        }
    }
}