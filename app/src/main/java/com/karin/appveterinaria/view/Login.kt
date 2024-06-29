package com.karin.appveterinaria.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karin.appveterinaria.R
import com.karin.appveterinaria.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController, authViewModel: AuthViewModel) {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("dev@dev.com") } // dev@dev.com
    var password by remember { mutableStateOf("123456") } // 123456
    val authState by authViewModel.authState.collectAsState()
    val errorState by authViewModel.errorState.collectAsState()
    
    
//    if (authState != null) {
//        navController.navigate("home") {
//            popUpTo("login") { inclusive = true }
//        }
//    }


    // Navegar a Home si el usuario está autenticado
    LaunchedEffect(authState) {
        if (authState != null) {  // Este cambio verifica que authState no sea nulo
            navController.navigate("post") {
                popUpTo("login") { inclusive = true }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBB031)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        ImageHeader()
//

        Box(
            modifier = Modifier
                .background(
                    Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                )
                .fillMaxHeight(),

            ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Bienvenido",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    maxLines = 1,
                    singleLine = true,

                    )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    maxLines = 1,
                    singleLine = true,

                    )

                Spacer(modifier = Modifier.height(12.dp))
                ElevatedButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    colors = androidx.compose.material3.ButtonDefaults
                        .buttonColors(containerColor = Color(0xFFe3b482)),
                    onClick = {
                        scope.launch {
                            authViewModel.login(email, password)
                        }
                    }

                ) {
                    Text("Ingresar")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 40.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF000000)
                    ),
                    onClick = {
                        navController.navigate("register")
                    }
                ) {
                    Text("¿No tienes una cuenta? Crear")
                }
                errorState?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, color = MaterialTheme.colors.error)
                }

            }
        }


    }

}

@Composable
fun ImageHeader() {
    Image(
        painter = painterResource(id = R.drawable.veterinaria_principal4),
        contentDescription = "imagen de veterinaria",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(5.dp)
    )
}

