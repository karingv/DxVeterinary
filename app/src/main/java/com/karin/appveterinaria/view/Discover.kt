package com.karin.appveterinaria.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Discover(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Discover") })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)){
            Text(text = "")
        }
    }
}