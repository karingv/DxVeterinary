package com.karin.appveterinaria.model

data class ProductModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var category: String = "", // "variados" or "medicina"
    var imageUrl: String = "" // URL de la imagen del producto
) {
    constructor() : this("", "", "", 0.0, "", "")
}
