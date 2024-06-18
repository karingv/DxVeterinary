package com.karin.appveterinaria.repository

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore
import com.karin.appveterinaria.model.CartItemModel
import com.karin.appveterinaria.model.ProductModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val cartItems = mutableStateListOf<CartItemModel>()

    fun getCartItems(): List<CartItemModel> = cartItems

    fun addItemToCart(product: ProductModel) {
        val existingItem = cartItems.find { it.productId == product.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cartItems.add(CartItemModel(
                productId = product.id,
                productName = product.name,
                productPrice = product.price,
                productImage = product.imageUrl,
                quantity = 1
            ))
        }
    }

    fun updateItemQuantity(productId: String, quantity: Int) {
        val item = cartItems.find { it.productId == productId }
        item?.let {
            it.quantity = quantity
            if (it.quantity <= 0) {
                cartItems.remove(it)
            }
        }
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.productPrice * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
