package com.karin.appveterinaria.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.karin.appveterinaria.model.UserModel
import com.karin.appveterinaria.repository.UserRepository
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

    val genderOptions = listOf("Masculino", "Femenino")
    val expanded = remember { mutableStateOf(false) }
    val selectedGenderIndex = remember { mutableStateOf(0) }

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
        IconButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(10.dp))
                .align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "Atras",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            "Crear cuenta", style = MaterialTheme.typography.h4, modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 30.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = name.value,
                modifier = Modifier.weight(0.5f),
                onValueChange = { name.value = it },
                label = { Text("Nombre") },
                maxLines = 1,
                singleLine = true,
            )
            Spacer(modifier = Modifier.padding(5.dp))
            OutlinedTextField(
                value = lastName.value,
                modifier = Modifier.weight(0.5f),
                onValueChange = { lastName.value = it },
                label = { Text("Apellidos") },
                maxLines = 1,
                singleLine = true,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = gender.value,
            onValueChange = { gender.value = it },
            label = { Text("Género") },
            maxLines = 1,
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Celular") },
            maxLines = 1,
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            maxLines = 1,
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            maxLines = 1,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))


        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp, top = 30.dp),
            colors = androidx.compose.material3.ButtonDefaults
                .buttonColors(containerColor = Color(0xFFe3b482)),

            onClick = {
                val user = UserModel(
                    email = email.value,
                    password = password.value,
                    name = name.value,
                    lastName = lastName.value,
                    gender = genderOptions[selectedGenderIndex.value],
                    phoneNumber = phoneNumber.value
                )
                authViewModel.register(user)
            }) {
            Text("Registrar")
        }
//        Spacer(modifier = Modifier.height(8.dp))
//
//        TextButton(
//
//            onClick = {
//                navController.navigate("login")
//            }) {
//            Text("Login")
//        }


    }
}
