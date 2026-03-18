package com.example.memoraapp.data.auth.repository

import androidx.core.net.toUri
import com.example.memoraapp.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRepositoryImplementation(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AuthRepository {

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

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<Unit> {
        return try {

            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid
                ?: return Result.failure(Exception("User ID null"))

            val userData = mapOf(
                "fullName" to name,
                "email" to email,
                "photoUrl" to null,
                "createdAt" to FieldValue.serverTimestamp(),
                "totalMemories" to 0
            )

            firestore
                .collection("users")
                .document(uid)
                .set(userData)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<Unit> {
        return try {

            val credential = GoogleAuthProvider.getCredential(idToken, null)

            val result = auth
                .signInWithCredential(credential)
                .await()

            val user = result.user
                ?: return Result.failure(Exception("User null"))

            val userDocRef = firestore
                .collection("users")
                .document(user.uid)

            val imageUrl =
                if (user.photoUrl?.toString()?.isLocalFile() == true) {
                    uploadImage(localUri = user.photoUrl.toString())
                } else {
                    user.photoUrl.toString()
                }

            val snapshot = userDocRef.get().await()

            if (!snapshot.exists()) {

                val userData = mapOf(
                    "fullName" to (user.displayName ?: ""),
                    "email" to user.email,
                    "photoUrl" to imageUrl,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "totalMemories" to 0
                )

                userDocRef
                    .set(userData, SetOptions.merge())
                    .await()
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}