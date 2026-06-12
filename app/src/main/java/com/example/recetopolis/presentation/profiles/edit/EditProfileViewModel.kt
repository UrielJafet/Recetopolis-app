package com.example.recetopolis.presentation.profiles.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetopolis.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val username: String = "",
    val email: String = "",
    val bio: String = "",
    val avatar: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(EditProfileUiState())
    val uiState: State<EditProfileUiState> = _uiState

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            userRepository.getCurrentUser()
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        username = user.username,
                        email = user.email
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

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username, error = null)
    }

    fun onBioChange(bio: String) {
        _uiState.value = _uiState.value.copy(bio = bio, error = null)
    }

    fun saveProfile() {
        val state = _uiState.value

        if (state.username.isBlank()) {
            _uiState.value = state.copy(error = "El nombre no puede estar vacío")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)

            userRepository.updateProfile(state.username, state.bio.takeIf { it.isNotBlank() }, state.avatar)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error al guardar"
                    )
                }
        }
    }

    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isSuccess = false)
    }
}