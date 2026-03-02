package com.example.memoraapp.data.repository

import com.example.memoraapp.data.mapper.toDomain
import com.example.memoraapp.data.mapper.toDto
import com.example.memoraapp.data.remote.MemoryDto
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreMemoryRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : MemoryRepository {

    private fun userMemoriesCollection(): CollectionReference {
        val userId = auth.currentUser?.uid
            ?: throw IllegalStateException("Usuário não autenticado")

        return firestore
            .collection("users")
            .document(userId)
            .collection("memories")
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
        val docRef = userMemoriesCollection().document()

        val dto = memory.toDto()

        docRef.set(dto)
    }

    override suspend fun update(memory: Memory) {
        userMemoriesCollection()
            .document(memory.id)
            .set(memory.toDto())
    }

    override suspend fun delete(memoryId: String) {
        userMemoriesCollection()
            .document(memoryId)
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