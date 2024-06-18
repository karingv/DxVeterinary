package com.karin.appveterinaria.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karin.appveterinaria.model.UserModel
import com.karin.appveterinaria.viewmodel.AuthViewModel

@Composable
fun Register(navController: NavController, authViewModel: AuthViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val authState = authViewModel.authState.collectAsState().value

    LaunchedEffect(authState) {
        if (authState != null) {
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Formulario de Registro", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Apellidos") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = gender.value,
            onValueChange = { gender.value = it },
            label = { Text("Género") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Celular") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val user = UserModel(
                email = email.value,
                password = password.value,
                name = name.value,
                lastName = lastName.value,
                gender = gender.value,
                phoneNumber = phoneNumber.value
            )
            authViewModel.register(user)
        }) {
            Text("Registrar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text("Login")
        }
    }
}

