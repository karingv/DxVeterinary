package com.karin.appveterinaria.viewmodel

import androidx.lifecycle.ViewModel
import com.karin.appveterinaria.model.CartItemModel
import com.karin.appveterinaria.model.ProductModel
import com.karin.appveterinaria.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemModel>>(emptyList())
    val cartItems: StateFlow<List<CartItemModel>> get() = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> get() = _totalPrice

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        _cartItems.value = cartRepository.getCartItems()
        _totalPrice.value = cartRepository.getTotalPrice()
    }

    fun addItemToCart(product: ProductModel) {
        cartRepository.addItemToCart(product)
        loadCartItems()
    }

    fun updateItemQuantity(productId: String, quantity: Int) {
        cartRepository.updateItemQuantity(productId, quantity)
        loadCartItems()
    }

    fun clearCart() {
        cartRepository.clearCart()
        loadCartItems()
    }
}
