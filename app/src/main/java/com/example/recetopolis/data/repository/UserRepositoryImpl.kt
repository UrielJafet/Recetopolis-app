package com.example.recetopolis.data.repository

import com.example.recetopolis.core.network.FakeApiService
import com.example.recetopolis.core.session.SessionManager
import com.example.recetopolis.domain.model.User
import com.example.recetopolis.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: FakeApiService,
    private val sessionManager: SessionManager
) : UserRepository {

    override suspend fun updateProfile(username: String, bio: String?, avatar: String?): Result<User> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("No autenticado")
            val updatedUser = apiService.updateProfile(token, username, bio, avatar)
            sessionManager.saveSession(token, updatedUser.id, updatedUser.username)
            Result.success(User(updatedUser.id, updatedUser.username, updatedUser.email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val token = sessionManager.authToken.first() ?: throw Exception("No autenticado")
            val user = apiService.getCurrentUser(token)
            Result.success(User(user.id, user.username, user.email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}