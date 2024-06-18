package com.karin.appveterinaria.model

data class PetModel(
    var id: String = "",
    var species: String = "",
    var name: String = "",
    var age: Int = 0,
    var weight: Double = 0.0,
    var numberOfVaccines: Int = 0,
    var userId: String = ""
){
    constructor() : this("", "", "", 0, 0.0, 0, "")
}

