package com.example.recetopolis.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recetopolis.domain.model.Recipe
import com.example.recetopolis.presentation.components.RecipeCard

@Composable
fun RecipeSection(
    title: String,
    recipes: List<Recipe>,
    onRecipeClick: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),  // ← Separación entre cards
            contentPadding = PaddingValues(horizontal = 4.dp)      // ← Padding inicial/final
        ) {
            items(recipes) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) },
                    modifier = Modifier
                        .width(160.dp)           // ← Ancho fijo
                        .height(200.dp)          // ← Alto fijo (o aspectRatio)
                )
            }
        }
    }
}