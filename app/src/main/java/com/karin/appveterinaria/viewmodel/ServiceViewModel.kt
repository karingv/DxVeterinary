package com.karin.appveterinaria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karin.appveterinaria.model.ReservationModel
import com.karin.appveterinaria.model.ServiceModel
import com.karin.appveterinaria.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
): ViewModel() {
    private val _services = MutableStateFlow<List<ServiceModel>>(emptyList())
    val services: StateFlow<List<ServiceModel>> get() = _services

    fun loadServices(){
        viewModelScope.launch {
            val serviceList = serviceRepository.getServices()
            _services.value = serviceList
        }
    }

}


