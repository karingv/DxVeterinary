package com.karin.appveterinaria.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        selectSpecie(species) { specie -> species = specie }

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { name = it },
            label = { Text("Nombre") },
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = age,
                modifier = Modifier.weight(0.5f),
                onValueChange = { age = it },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = weight,
                modifier = Modifier.weight(0.5f),
                onValueChange = { weight = it },
                label = { Text("Peso") },
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = numberOfVaccines,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { numberOfVaccines = it },
            label = { Text("Número de Vacunas") },
            keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        FilledTonalButton(
            onClick = {
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
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Guardar Mascota", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))
//        Text("Mascotas Registradas", style = MaterialTheme.typography.h6)
//        Spacer(modifier = Modifier.height(8.dp))

//        LazyColumn {
//            items(pets) { pet ->
//                PetItem(pet = pet)
//            }
//        }
    }
}

@Composable
fun selectSpecie(selectSpecie: String, onSpieceSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val speciesOptions = listOf("Perro", "Gato", "Ave", "Conejo", "Cuy")
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .border(border = BorderStroke(1.dp, MaterialTheme.colors.primary), shape = RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(12.dp).fillMaxWidth()
    ) {
        Text(
            text = selectSpecie.ifEmpty { "Selecciona una especie" },
            Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            speciesOptions.forEach { specie ->
                DropdownMenuItem(onClick = {
                    onSpieceSelected(specie)
                    expanded = false
                }) {
                    Text(text = specie)
                }
            }
        }
    }
}

@Composable
fun PetItem(pet: PetModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Especie: ${pet.species}", style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface))
            Text("Nombre: ${pet.name}", style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface))
            Text("Edad: ${pet.age}", style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface))
            Text("Peso: ${pet.weight}", style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface))
            Text("Número de Vacunas: ${pet.numberOfVaccines}", style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface))
        }
    }
}