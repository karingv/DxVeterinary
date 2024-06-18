package com.karin.appveterinaria.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.karin.appveterinaria.model.ReservationModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReservationRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun addReservation(reservation: ReservationModel) {
        firestore.collection("reservations").add(reservation).await()
    }

    suspend fun getReservations(userId: String): List<ReservationModel> {
        return try {
            val reservation = firestore.collection("reservations").whereEqualTo("userId", userId).get().await()
            reservation.toObjects(ReservationModel::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateReservationStatus(reservationId: String, status: String) {
        firestore.collection("reservations").document(reservationId).update("status", status).await()
    }

    suspend fun deleteReservation(reservationId: String) {
        firestore.collection("reservations").document(reservationId).delete().await()
    }
}