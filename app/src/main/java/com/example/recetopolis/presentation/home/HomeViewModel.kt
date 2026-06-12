package com.example.recetopolis.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetopolis.domain.model.Recipe
import com.example.recetopolis.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val categories: List<String> = listOf("Todas", "Mexicana", "Italiana", "Japonesa", "Española", "Postres"),
    val selectedCategory: String = "Todas",
    val searchQuery: String = "",
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            recipeRepository.getRecipes()
                .onSuccess { recipes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recipes = recipes
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)

        if (query.isEmpty()) {
            loadRecipes()
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            recipeRepository.searchRecipes(query, null, null)
                .onSuccess { recipes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recipes = recipes
                    )
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)

        if (category == "Todas") {
            loadRecipes()
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            recipeRepository.searchRecipes(null, category, null)
                .onSuccess { recipes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recipes = recipes
                    )
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
        }
    }
}