package com.karin.appveterinaria.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.karin.appveterinaria.model.UserModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    suspend fun register(user: UserModel): Result<FirebaseUser?> {
        return try {
            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            val firebaseUser = authResult.user ?: throw Exception("Error en el registro de usuario")
            val userId = firebaseUser.uid
            val userMap = mapOf(
                "id" to userId,
                "email" to user.email,
                "name" to user.name,
                "lastName" to user.lastName,
                "gender" to user.gender,
                "phoneNumber" to user.phoneNumber
            )
            firestore.collection("users").document(firebaseUser.uid).set(userMap).await()

            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(authResult.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}