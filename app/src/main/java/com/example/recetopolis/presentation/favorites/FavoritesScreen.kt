package com.example.recetopolis.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recetopolis.domain.model.Recipe
import com.example.recetopolis.presentation.components.RecipeCard
import com.example.recetopolis.presentation.navigation.BottomBar

@Composable
fun FavoritesScreen(
    navController: NavController,
    onNavigateToRecipeDetail: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        FavoritesContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            uiState = uiState,
            onRecipeClick = onNavigateToRecipeDetail,
            onRemoveFavorite = viewModel::removeFavorite
        )
    }
}

@Composable
private fun FavoritesContent(
    modifier: Modifier,
    uiState: FavoritesUiState,
    onRecipeClick: (String) -> Unit,
    onRemoveFavorite: (String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Mis Favoritos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Tus recetas guardadas",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(
                text = uiState.error,
                color = MaterialTheme.colorScheme.error
            )
        } else if (uiState.favorites.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                    Text(
                        text = "No tienes favoritos",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "Guarda recetas que te gusten",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            // Lista de favoritos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),  // ← Separación vertical
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(uiState.favorites) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        onFavoriteClick = { onRemoveFavorite(recipe.id) },
                        isFavorite = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)  // ← Alto fijo, ancho completo
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteItem(
    recipe: Recipe,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        RecipeCard(
            recipe = recipe,
            onClick = onClick
        )

        // Botón eliminar en esquina
        IconButton(
            onClick = onRemove,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar de favoritos",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}