package com.karin.appveterinaria.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ServiceModel (
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var availableDates: List<AvailabilityModel> = listOf()

){
    constructor() : this("", "", "", listOf())
}

@IgnoreExtraProperties
data class AvailabilityModel(
    var date: String = "",   // Formato de fecha por ejemplo "2024-06-07"
    var timeSlots: List<TimeSlot> = listOf()
){
    constructor() : this("", listOf())
}

@IgnoreExtraProperties
data class TimeSlot(
    var time: String = "",   // Formato de hora por ejemplo "14:00"
    var isAvailable: Boolean = true  // Indica si el horario está disponible
) {
    constructor() : this("", true)
}
