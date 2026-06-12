package com.example.recetopolis.data.repository

import com.example.recetopolis.core.network.FakeApiService
import com.example.recetopolis.core.session.SessionManager
import com.example.recetopolis.data.remote.dto.LoginRequest
import com.example.recetopolis.data.remote.dto.RegisterRequest
import com.example.recetopolis.domain.model.User
import com.example.recetopolis.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first

class AuthRepositoryImpl(
    private val apiService: FakeApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            sessionManager.saveSession(response.token, response.user.id, response.user.username)
            Result.success(User(response.user.id, response.user.username, response.user.email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(username: String, email: String, password: String): Result<User> {
        return try {
            val response = apiService.register(RegisterRequest(username, email, password))
            sessionManager.saveSession(response.token, response.user.id, response.user.username)
            Result.success(User(response.user.id, response.user.username, response.user.email))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        sessionManager.clearSession()
    }
}