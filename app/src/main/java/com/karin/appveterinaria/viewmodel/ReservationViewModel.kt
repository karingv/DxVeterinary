package com.karin.appveterinaria.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.karin.appveterinaria.model.ReservationModel
import com.karin.appveterinaria.model.ServiceModel
import com.karin.appveterinaria.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
): ViewModel(){
    private val _reservations = MutableStateFlow<List<ReservationModel>>(emptyList())
    val reservations: StateFlow<List<ReservationModel>> get() = _reservations

    fun addReservation(reservation: ReservationModel){
        viewModelScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                val serviceRef = db.collection("services").document(reservation.serviceId)
                val serviceSnapshot = serviceRef.get().await()

                if (serviceSnapshot.exists()) {
                    val service = serviceSnapshot.toObject(ServiceModel::class.java)
                    service?.let {
                        val availability = it.availableDates.find { it.date == reservation.date }
                        availability?.let { avail ->
                            val timeSlot = avail.timeSlots.find { it.time == reservation.time }
                            if (timeSlot != null && timeSlot.isAvailable) {
                                // Marcar el horario como no disponible
                                timeSlot.isAvailable = false

                                // Actualizar el documento del servicio con el horario no disponible
                                serviceRef.set(it).await()

                                // Guardar la reserva con el ID generado
                                reservationRepository.addReservation(reservation)

                                // Recargar la lista de reservas después de agregar una nueva
                                loadReservations(reservation.userId)
                            } else {
                                // Manejar el caso donde el horario no está disponible
                                Log.e("ReservationViewModel", "Time slot is not available")
                            }
                        } ?: Log.e("ReservationViewModel", "Availability not found for date: ${reservation.date}")
                    } ?: Log.e("ReservationViewModel", "Service not found with id: ${reservation.serviceId}")
                } else {
                    Log.e("ReservationViewModel", "Service document does not exist with id: ${reservation.serviceId}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error adding reservation", e)
            }
        }
    }



    fun loadReservations(userId: String) {
        viewModelScope.launch {
            try {
                val reservationList = reservationRepository.getReservations(userId)
                _reservations.value = reservationList
            } catch (e: Exception) {
                Log.e("Error", "Error loading reservations", e)
            }
        }
    }

/*
    fun updateReservationStatus(reservationId: String, status: String) {
        viewModelScope.launch {
            reservationRepository.updateReservationStatus(reservationId, status)
            // Actualizar la lista de reservas
            _reservations.value = _reservations.value.map {
                if (it.id == reservationId) it.copy(status = status) else it
            }
        }
    }
*/

    fun cancelReservation(reservation: ReservationModel) {
        viewModelScope.launch {
            try {
                if (reservation.id.isNotEmpty()) {
                    Log.d("ReservationViewModel", "Attempting to delete reservation with ID: ${reservation.id}")
                    val db = FirebaseFirestore.getInstance()
                    val reservationRef = db.collection("reservations").document(reservation.id)

                    // Eliminar la reserva
                    reservationRef.delete().await()
                    Log.d("Eliminar reserva", "Successfully deleted reservation with ID: ${reservation.id}")

                    // Remover la reserva localmente después de la eliminación exitosa
                    _reservations.value = _reservations.value.filter { it.id != reservation.id }

                    // Hacer que el horario cancelado esté disponible de nuevo
                    val serviceRef = db.collection("services").document(reservation.serviceId)
                    val serviceSnapshot = serviceRef.get().await()

                    if (serviceSnapshot.exists()) {
                        val service = serviceSnapshot.toObject(ServiceModel::class.java)
                        service?.let {
                            val availability = it.availableDates.find { it.date == reservation.date }
                            availability?.let { avail ->
                                val timeSlot = avail.timeSlots.find { it.time == reservation.time }
                                timeSlot?.isAvailable = true
                                
                            }
                        }
                    }


                } else {
                    Log.e("NoCargaReserva", "Reservation ID is empty")
                }
            } catch (e: Exception) {
                Log.e("Error", "Error cancelling reservation", e)
            }
        }
    }
}