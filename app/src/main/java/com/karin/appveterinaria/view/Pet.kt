package com.karin.appveterinaria.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karin.appveterinaria.model.PetModel
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel



@Composable
fun Pet(navController: NavController, petViewModel: PetViewModel, authViewModel: AuthViewModel) {
    val user = authViewModel.authState.collectAsState().value
    val pets = petViewModel.pets.collectAsState().value

    var species by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var numberOfVaccines by remember { mutableStateOf("") }

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
        Text("Agregar Mascota", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        selectSpecie(species){ specie -> species = specie}
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Peso") },
            keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = numberOfVaccines,
            onValueChange = { numberOfVaccines = it },
            label = { Text("Número de Vacunas") },
            keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (user != null) {
                val pet = PetModel(
                    id = "",  // El ID se generará automáticamente en el repositorio
                    species = species,
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    weight = weight.toDoubleOrNull() ?: 0.0,
                    numberOfVaccines = numberOfVaccines.toIntOrNull() ?: 0,
                    userId = user.uid
                )
                petViewModel.addPet(pet)
            }
        }) {
            Text("Guardar Mascota")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Mascotas Registradas", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(pets) { pet ->
                PetItem(pet = pet)
            }
        }
    }
}

@Composable
fun selectSpecie(selectSpecie : String, onSpieceSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val speciesOptions = listOf("Perro", "Gato", "Ave", "Conejo", "Cuy")
    Box(modifier = Modifier
        .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = selectSpecie.ifEmpty { "Selecciona una especie" },
            Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            speciesOptions.forEach { specie ->
                DropdownMenuItem(onClick = {
                    onSpieceSelected(specie)
                    expanded = false
                }) {
                    androidx.compose.material3.Text(text = specie)
                }
            }
        }
    }
}

@Composable
fun PetItem(pet: PetModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colors.primary)
            .padding(8.dp)
    ) {
        Text("Especie: ${pet.species}")
        Text("Nombre: ${pet.name}")
        Text("Edad: ${pet.age}")
        Text("Peso: ${pet.weight}")
        Text("Número de Vacunas: ${pet.numberOfVaccines}")
        Divider()
    }
}
