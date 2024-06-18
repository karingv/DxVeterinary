package com.karin.appveterinaria.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ReservationModel(
    var id: String = "",
    var userId: String = "",
    var petName: String = "",
    var serviceId: String = "",
    var date: String = "",
    var time: String = "",
    var status: String = "pendiente"
) {
    constructor() : this("", "", "", "", "", "", "")
}