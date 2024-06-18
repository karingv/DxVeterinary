package com.karin.appveterinaria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.karin.appveterinaria.model.UserModel
import com.karin.appveterinaria.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<FirebaseUser?>(userRepository.currentUser)
    val authState: StateFlow<FirebaseUser?> get() = _authState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    fun register(user: UserModel) {
        viewModelScope.launch {
            val result = userRepository.register(user)
            result.onSuccess {
                _authState.value = it
            }.onFailure {
                _errorState.value = it.message
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(email, password)
            result.onSuccess {
                _authState.value = it
            }.onFailure {
                _errorState.value = it.message
            }
        }
    }

    fun logout() {
        userRepository.logout()
        _authState.value = null
    }
}