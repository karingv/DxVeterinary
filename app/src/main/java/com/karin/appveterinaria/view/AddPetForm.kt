package com.karin.appveterinaria.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karin.appveterinaria.model.PetModel
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel

@Composable
fun AddPetForm(navController: NavController, petViewModel: PetViewModel, authViewModel: AuthViewModel) {
    val user = authViewModel.authState.collectAsState().value

    var species by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var numberOfVaccines by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agregar Mascota", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        selectSpecie(species) { specie -> species = specie }

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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
    }
}
