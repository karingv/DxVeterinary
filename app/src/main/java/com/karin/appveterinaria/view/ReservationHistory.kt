package com.karin.appveterinaria.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.ReservationViewModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit


@Composable
fun ReservationHistory(
    navController: NavController,
    authViewModel: AuthViewModel,
    reservationViewModel: ReservationViewModel = hiltViewModel()
) {
    val user = authViewModel.authState.collectAsState().value
    val reservations by reservationViewModel.reservations.collectAsState()

    LaunchedEffect(user) {
        if (user != null) {
            reservationViewModel.loadReservations(user.uid)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Historial de Reservas", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(reservations) { reservation ->
                val dateTime = LocalDateTime.parse("${reservation.date}T${reservation.time}")
                val now = LocalDateTime.now()
                val canCancel = ChronoUnit.HOURS.between(now, dateTime) >= 24

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                       // Text("ID: ${reservation.id}")
                        Text("Servicio: ${reservation.serviceId}")
                        Text("Fecha: ${reservation.date}")
                        Text("Hora: ${reservation.time}")
                        Text("Mascota: ${reservation.petName}")
                        Text("Estado: ${reservation.status}")
                    }
                    if (canCancel) {
                        Button(onClick = { reservationViewModel.cancelReservation(reservation) }) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}