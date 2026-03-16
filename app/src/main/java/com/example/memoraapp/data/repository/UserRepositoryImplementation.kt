package com.example.memoraapp.data.repository

import androidx.core.net.toUri
import com.example.memoraapp.data.mapper.toDomain
import com.example.memoraapp.data.mapper.toDto
import com.example.memoraapp.data.remote.UserDto
import com.example.memoraapp.domain.User
import com.example.memoraapp.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepositoryImplementation(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
): UserRepository {

    private fun String.isLocalFile(): Boolean {
        return startsWith("content://") || startsWith("file://")
    }

    private suspend fun uploadImage(
        localUri: String
    ): String {

        val userId = auth.currentUser?.uid
            ?: throw IllegalStateException("Usuário não autenticado")

        val ref = storage.reference
            .child("users/$userId/profile.jpg")

        ref.putFile(localUri.toUri()).await()

        return ref.downloadUrl.await().toString()
    }

    override suspend fun getUser(userId: String): User? {
        val document = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        val user = document.toObject(UserDto::class.java)?.copy(totalMemories = getMemoriesCount(userId))
        return user?.toDomain(document.id)
    }

    override suspend fun getMemoriesCount(userId: String): Long {

        val snapshot = firestore
            .collection("users/$userId/memories")
            .get()
            .await()

        return snapshot.size().toLong()
    }

    override suspend fun updateUser(user: User) {
        val imageUrl =
            if (user.photoUrl?.isLocalFile() == true) {
                uploadImage(localUri = user.photoUrl)
            } else {
                user.photoUrl
            }

        val updatedUser = user.copy(photoUrl = imageUrl)

        firestore.collection("users")
            .document(user.id)
            .set(updatedUser.toDto())
            .await()
    }

    override suspend fun deleteUser(userId: String) {

        val user = auth.currentUser
            ?: throw Exception("Usuário não autenticado")

        val uid = user.uid

        if (userId == uid) {
            firestore.collection("users")
                .document(uid)
                .delete()
                .await()

            try {
                storage.reference
                    .child("users/$uid/profile.jpg")
                    .delete()
                    .await()
            } catch (e: Exception) {}

            user.delete().await()
        }
    }
}