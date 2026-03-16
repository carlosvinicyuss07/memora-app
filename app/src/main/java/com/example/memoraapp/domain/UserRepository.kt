package com.example.memoraapp.domain

interface UserRepository {

    suspend fun getUser(userId: String): User?
    suspend fun getMemoriesCount(userId: String): Long
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
}
