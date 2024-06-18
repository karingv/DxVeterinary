package com.karin.appveterinaria.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.karin.appveterinaria.model.AvailabilityModel
import com.karin.appveterinaria.model.PetModel
import com.karin.appveterinaria.model.ReservationModel
import com.karin.appveterinaria.model.ServiceModel
import com.karin.appveterinaria.model.TimeSlot
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel
import com.karin.appveterinaria.viewmodel.ReservationViewModel
import com.karin.appveterinaria.viewmodel.ServiceViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.UUID


@Composable
fun Reservation(
    navController: NavController,
    serviceViewModel: ServiceViewModel,
    petViewModel: PetViewModel,
    authViewModel: AuthViewModel,
    reservationViewModel: ReservationViewModel
) {
    val user = authViewModel.authState.collectAsState().value
    val services by serviceViewModel.services.collectAsState()
    val pets by petViewModel.pets.collectAsState()
    val reservations by reservationViewModel.reservations.collectAsState()

    var selectedService by remember { mutableStateOf<ServiceModel?>(null) }
    var selectedPet by remember { mutableStateOf<PetModel?>(null) }
    var selectedDate by remember { mutableStateOf<AvailabilityModel?>(null) }
    var selectedTimeSlot  by remember { mutableStateOf<TimeSlot?>(null) }

    LaunchedEffect(user) {
        if (user != null) {
            petViewModel.loadUserPets(user.uid)
            serviceViewModel.loadServices()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reservar Servicio", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        Log.d("Reservation", "Servicios cargados: $services" )
        // Seleccionar Servicio
        DropdownMenu(
            label = "Seleccionar servicio",
            options = services,
            selectedOption = selectedService,
            onOptionSelected = { selectedService = it },
            displayText = { it.name }
        )
        Log.d("Reservation", "Servicio seleccionado: $selectedService")
        Spacer(modifier = Modifier.height(8.dp))

        // Seleccionar Mascota
        DropdownMenu(
            label = "Seleccionar mascota",
            options = pets,
            selectedOption = selectedPet,
            onOptionSelected = { selectedPet = it },
            displayText = { it.name }
        )
        Log.d("Reservation", "Mascotas cargadas: $pets")
        Spacer(modifier = Modifier.height(8.dp))

        // Seleccionar Fecha
        if (selectedService != null) {
            CalendarPicker(
                availableDates = selectedService!!.availableDates,
                onDateSelected = { date ->
                    selectedDate = date
                    selectedTimeSlot = null // Reset time slot selection
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (selectedDate != null) {
                DropdownMenu(
                    label = "Seleccionar Horario",
                    options = selectedDate!!.timeSlots.filter { it.isAvailable },
                    selectedOption = selectedTimeSlot,
                    onOptionSelected = { selectedTimeSlot = it },
                    displayText = { it.time }
                )
            }
        }
        Log.d("Reservation", "Fecha seleccionada: $selectedDate")
        Log.d("Reservation", "Horario seleccionado: $selectedTimeSlot")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (user != null && selectedService != null && selectedPet != null && selectedDate != null && selectedTimeSlot != null) {
                val isDuplicate = reservations.any {
                    it.serviceId == selectedService!!.id &&
                            it.date == selectedDate!!.date &&
                            it.time == selectedTimeSlot!!.time
                }

                if (!isDuplicate) {
                    val reservationId = UUID.randomUUID().toString()  // Generar ID único para la reserva
                    val reservation = ReservationModel(
                        id = reservationId,
                        userId = user.uid,
                        serviceId = selectedService!!.id,
                        petName = selectedPet!!.name,
                        date = selectedDate!!.date,
                        time = selectedTimeSlot!!.time,
                        status = "pendiente"
                    )
                    reservationViewModel.addReservation(reservation)
                    navController.popBackStack()
                } else {
                    // Show error message for duplicate reservation
                    // You can use a Snackbar or a dialog to show this message
                    Log.e("Reservation", "Reserva duplicada")
                }
            }
        }) {
            Text("Reservar")
        }
    }
}

@Composable
fun <T> DropdownMenu(
    label: String,
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    displayText: (T) -> String
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = selectedOption?.let(displayText) ?: label,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp)
                .border(1.dp, MaterialTheme.colors.primary)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }) {
                    Text(displayText(option))
                    //Text(option.toString())
                }
            }
        }
    }
}

@Composable
fun CalendarPicker(
    availableDates: List<AvailabilityModel>,
    onDateSelected: (AvailabilityModel) -> Unit
) {
    var selectedDate by remember { mutableStateOf<AvailabilityModel?>(null) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Seleccionar Fecha", style = MaterialTheme.typography.h6)
        LazyColumn {
            items(availableDates) { date ->
                val localDate = LocalDate.parse(date.date, dateFormatter)
                Text(
                    text = dateFormatter.format(localDate),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedDate = date
                            onDateSelected(date)
                        }
                        .padding(8.dp)
                        .border(
                            1.dp,
                            if (selectedDate == date) MaterialTheme.colors.primary else Color.Gray
                        )
                        .padding(8.dp),
                    style = if (selectedDate == date) MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary) else MaterialTheme.typography.body1
                )
            }
        }
    }
}


@Preview
@Composable
fun ReservationPreview() {
    val navController = rememberNavController()
    val serviceViewModel: ServiceViewModel = hiltViewModel()
    val petViewModel: PetViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val reservationViewModel: ReservationViewModel = hiltViewModel()

    Reservation(
        navController = navController,
        serviceViewModel = serviceViewModel,
        petViewModel = petViewModel,
        authViewModel = authViewModel,
        reservationViewModel = reservationViewModel
    )
}