package com.karin.appveterinaria.repository
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.karin.appveterinaria.model.PetModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun addPet(pet: PetModel) {
        val newPet = firestore.collection("pets").document()
        pet.id = newPet.id
        newPet.set(pet).await()
    }
    /*
    suspend fun addPet(pet: PetModel): Result<Unit> {
        return try {
            val petMap = mapOf(
                "species" to pet.species,
                "name" to pet.name,
                "age" to pet.age,
                "weight" to pet.weight,
                "numberOfVaccines" to pet.numberOfVaccines,
                "userId" to pet.userId
            )
            firestore.collection("pets").add(petMap).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
     */

    suspend fun getUserPets(userId: String): List<PetModel> {
        return try {
            val snapshot = firestore.collection("pets")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            Log.d("PetRepository", "Pets loaded for user $userId: ${snapshot.documents.size}")
            snapshot.toObjects(PetModel::class.java)
        } catch (e: Exception) {
            Log.e("PetRepository", "Error loading pets: ", e)
            emptyList()
        }
    }
}