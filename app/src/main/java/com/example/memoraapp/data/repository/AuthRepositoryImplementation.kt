package com.example.memoraapp.data.repository

import com.example.memoraapp.data.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class AuthRepositoryImplementation(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

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
                "name" to name,
                "email" to email,
                "photoUrl" to null,
                "createdAt" to FieldValue.serverTimestamp()
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

            val isNewUser = result.additionalUserInfo?.isNewUser == true

            if (isNewUser) {

                val userData = mapOf(
                    "name" to (user.displayName ?: ""),
                    "email" to user.email,
                    "photoUrl" to user.photoUrl?.toString(),
                    "createdAt" to FieldValue.serverTimestamp()
                )

                firestore
                    .collection("users")
                    .document(user.uid)
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