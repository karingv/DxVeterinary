package com.karin.appveterinaria.model

data class CartItemModel(
    val productId: String,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    var quantity: Int
){
    constructor() : this("", "", 0.0, "", 0)
}

