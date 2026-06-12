package com.example.recetopolis.domain.repository

import com.example.recetopolis.domain.model.User

interface UserRepository {
    suspend fun updateProfile(username: String, bio: String?, avatar: String?): Result<User>
    suspend fun getCurrentUser(): Result<User>
}