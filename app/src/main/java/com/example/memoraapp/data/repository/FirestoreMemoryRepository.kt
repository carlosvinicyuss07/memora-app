package com.example.memoraapp.data.repository

import android.net.Uri
import com.example.memoraapp.data.mapper.toDomain
import com.example.memoraapp.data.mapper.toDto
import com.example.memoraapp.data.remote.MemoryDto
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import androidx.core.net.toUri

class FirestoreMemoryRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) : MemoryRepository {

    private fun userMemoriesCollection(): CollectionReference {
        val userId = auth.currentUser?.uid
            ?: throw IllegalStateException("Usuário não autenticado")

        return firestore
            .collection("users")
            .document(userId)
            .collection("memories")
    }

    private suspend fun uploadImage(
        userId: String,
        memoryId: String,
        localUri: String
    ): String {

        val ref = storage.reference
            .child("users/$userId/memories/$memoryId.jpg")

        ref.putFile(localUri.toUri()).await()

        return ref.downloadUrl.await().toString()
    }

    override fun getAllMemories(): Flow<List<Memory>> = callbackFlow {
        val listener = userMemoriesCollection()
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val memories = snapshot?.documents?.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(MemoryDto::class.java)?.toDomain(documentSnapshot.id)
                } ?: emptyList()

                trySend(memories)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun insert(memory: Memory) {

        val userId = auth.currentUser!!.uid
        val docRef = userMemoriesCollection().document()

        var imageUrl: String? = null

        if (memory.imageUri != null) {
            imageUrl = uploadImage(
                userId = userId,
                memoryId = docRef.id,
                localUri = memory.imageUri
            )
        }

        val memoryWithId = memory.copy(id = docRef.id, imageUri = imageUrl)

        docRef.set(memoryWithId.toDto()).await()
    }

    override suspend fun update(memory: Memory) {

        val userId = auth.currentUser!!.uid

        var imageUrl = memory.imageUri

        if (memory.imageUri?.startsWith("content://") == true) {

            imageUrl = uploadImage(
                userId = userId,
                memoryId = memory.id,
                localUri = memory.imageUri
            )
        }

        val updated = memory.copy(imageUri = imageUrl)

        userMemoriesCollection()
            .document(memory.id)
            .set(updated.toDto())
            .await()
    }

    override suspend fun delete(memoryId: String) {

        val userId = auth.currentUser!!.uid

        userMemoriesCollection()
            .document(memoryId)
            .delete()
            .await()

        storage.reference
            .child("users/$userId/memories/$memoryId.jpg")
            .delete()
    }

    override suspend fun getMemoryById(id: String): Memory? {
        val snapshot = userMemoriesCollection()
            .document(id)
            .get()
            .await()

        return snapshot.toObject(MemoryDto::class.java)
            ?.toDomain(snapshot.id)
    }
}