package com.karin.appveterinaria.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.karin.appveterinaria.model.ProductModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getProductsByCategory(category: String): List<ProductModel> {
        return try {
            val snapshot = db.collection("products")
                .whereEqualTo("category", category)
                .get()
                .await()
            snapshot.toObjects(ProductModel::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addProduct(product: ProductModel) {
        val newProductRef = db.collection("products").document()
        product.id = newProductRef.id
        newProductRef.set(product).await()
    }

    suspend fun deleteProduct(productId: String) {
        db.collection("products").document(productId).delete().await()
    }
}