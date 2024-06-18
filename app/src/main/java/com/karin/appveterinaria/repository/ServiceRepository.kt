package com.karin.appveterinaria.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.karin.appveterinaria.model.ServiceModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val firestore: FirebaseFirestore
){
    suspend fun getServices() : List<ServiceModel>{
        return try {
            val service = firestore.collection("services").get().await()
            service.toObjects(ServiceModel::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
