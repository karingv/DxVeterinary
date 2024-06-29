package com.karin.appveterinaria.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel

@Composable
fun PetList(petViewModel: PetViewModel, authViewModel: AuthViewModel) {
    val user = authViewModel.authState.collectAsState().value
    val pets = petViewModel.pets.collectAsState().value

    LaunchedEffect(user) {
        if (user != null) {
            petViewModel.loadUserPets(user.uid)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mascotas Registradas", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(pets) { pet ->
                PetItem(pet = pet)
            }
        }
    }
}
