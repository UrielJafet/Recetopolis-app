package com.example.recetopolis.presentation.recipes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetopolis.domain.model.Recipe
import com.example.recetopolis.domain.repository.FavoriteRepository
import com.example.recetopolis.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeDetailUiState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = mutableStateOf(RecipeDetailUiState())
    val uiState: State<RecipeDetailUiState> = _uiState

    private val recipeId: String = checkNotNull(savedStateHandle["recipeId"])

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            recipeRepository.getRecipeById(recipeId)
                .onSuccess { recipe ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recipe = recipe
                    )
                    checkIsFavorite()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }

    private fun checkIsFavorite() {
        viewModelScope.launch {
            favoriteRepository.isFavorite(recipeId)
                .onSuccess { isFav ->
                    _uiState.value = _uiState.value.copy(isFavorite = isFav)
                }
        }
    }

    fun toggleFavorite() {
        val currentIsFavorite = _uiState.value.isFavorite

        viewModelScope.launch {
            if (currentIsFavorite) {
                favoriteRepository.removeFavorite(recipeId)
                    .onSuccess {
                        _uiState.value = _uiState.value.copy(isFavorite = false)
                    }
            } else {
                favoriteRepository.addFavorite(recipeId)
                    .onSuccess {
                        _uiState.value = _uiState.value.copy(isFavorite = true)
                    }
            }
        }
    }
}