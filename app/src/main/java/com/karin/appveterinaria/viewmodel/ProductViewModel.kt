package com.karin.appveterinaria.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karin.appveterinaria.model.ProductModel
import com.karin.appveterinaria.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> get() = _products

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                Log.d("ProductViewModel", "Loading products for category: $category")
                val productList = productRepository.getProductsByCategory(category)
                _products.value = productList
                Log.d("ProductViewModel", "Loaded products: ${productList.size}")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error loading products", e)
            }
        }
    }

    fun addProduct(product: ProductModel) {
        viewModelScope.launch {
            try {
                Log.d("ProductViewModel", "Adding product: ${product.name}")
                productRepository.addProduct(product)
                loadProductsByCategory(product.category)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error adding product", e)
            }
        }
    }

    fun deleteProduct(productId: String, category: String) {
        viewModelScope.launch {
            try {
                Log.d("ProductViewModel", "Deleting product: $productId")
                productRepository.deleteProduct(productId)
                loadProductsByCategory(category)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error deleting product", e)
            }
        }
    }
}
