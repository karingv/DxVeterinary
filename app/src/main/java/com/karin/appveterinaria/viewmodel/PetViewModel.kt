package com.karin.appveterinaria.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karin.appveterinaria.model.PetModel
import com.karin.appveterinaria.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petRepository: PetRepository
) : ViewModel() {

    private val _pets = MutableStateFlow<List<PetModel>>(emptyList())
    val pets: StateFlow<List<PetModel>> get() = _pets

    fun addPet(pet: PetModel) {
        viewModelScope.launch {
            petRepository.addPet(pet)
            Log.d("PetViewModel", "Pet added: $pet")
            loadUserPets(pet.userId)
        }
    }

    fun loadUserPets(userId: String) {
        viewModelScope.launch {
            val petList = petRepository.getUserPets(userId)
            _pets.value = petList
        }
    }
}