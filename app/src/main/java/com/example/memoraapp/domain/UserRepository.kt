package com.example.memoraapp.domain

interface UserRepository {

    suspend fun getUser(userId: String): User?
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
}
