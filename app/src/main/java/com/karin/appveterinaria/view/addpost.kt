package com.karin.appveterinaria.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.Timestamp
import com.karin.appveterinaria.model.Post
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPost(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    viewModel: PostViewModel = hiltViewModel(),
) {
    var Titulo by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var image by remember { mutableStateOf<Uri?>(null) } // Para almacenar la URI de la imagen seleccionada
    var loading by remember { mutableStateOf(false) } // Estado para controlar el estado de carga

    val postResult by viewModel.addPostResult.observeAsState()
    val user by authViewModel.user.observeAsState()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(postResult) {
        if (postResult == true) {
            navController.navigate("post")
        }
    }

    LaunchedEffect(authState) {
        authState?.let {
            authViewModel.loadUserData(it.uid) // Cargar los datos del usuario al iniciar
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(3.dp),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("post")
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                    }
                },
                title = { Text("Agregar Publicación") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            user?.let { userModel ->
                Text(text = "Bienvenido, ${userModel.name}", color = Color.Black)
            }

            OutlinedTextField(
                value = Titulo,
                onValueChange = { Titulo = it },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Contenido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            selectImage(navController) { uri ->
                image = uri
            }

            image?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(shape = CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de publicar
            ElevatedButton(
                onClick = {
                    if (Titulo.isNotBlank()) {
                        loading = true // Activar el estado de carga
                        user?.let { userModel ->
                            viewModel.addPost(
                                Post(
                                    Titulo = Titulo,
                                    content = content,
                                    timestamp = Timestamp.now(),
                                    userID = userModel.id,
                                    userName = userModel.name,
                                ),
                                image
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3124F3)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                enabled = !loading // Deshabilitar el botón durante la carga
            ) {
                if (loading) {
                    CircularProgressIndicator(color = Color.Black)
                } else {
                    Text("Publicar", color = Color.Black)
                }
            }
        }

    }
}


// Función para seleccionar una imagen desde la galería
@Composable
private fun selectImage(navController: NavController, onImageSelected: (Uri) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImageSelected(it) }
        }

    IconButton(onClick = {
        launcher.launch("image/*")
    }) {
        Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Seleccionar imagen")
    }
}
