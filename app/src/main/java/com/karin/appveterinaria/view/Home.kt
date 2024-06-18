package com.karin.appveterinaria.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("reservation") },
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Text("Servicios")
            }
            /*
            Button(
                onClick = { /* Navegar a Grooming */ },
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Text("Grooming")
            }
             */
            Button(
                onClick = {
                    Log.d("Home", "Navigating to products/medicina")
                    navController.navigate("products/medicina")
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Text("Medicinas")
            }
            Button(
                onClick = {
                    Log.d("Home", "Navigating to products/variados")
                    navController.navigate("products/variados")
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Text("Productos")
            }
        }
    }
}
