package com.karin.appveterinaria.view


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun Main() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Veterinaria") })
        },
        content = { innerPadding ->
            Text(
                text = "Bienvenido a la aplicación de Veterinaria",
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}