package com.example.memoraapp.data.repository

import com.example.memoraapp.domain.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun getUser(uid: String): User? {
        val document = firestore.collection("users")
            .document(uid)
            .get()
            .await()

        return document.toObject(User::class.java)
    }
}