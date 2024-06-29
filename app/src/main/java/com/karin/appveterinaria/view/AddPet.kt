package com.karin.appveterinaria.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.CartViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel
import com.karin.appveterinaria.viewmodel.ProductViewModel

@Composable
fun AddPet(navController: NavController, petViewModel: PetViewModel, authViewModel: AuthViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                }
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Agregar Mascota") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Mascotas Registradas") }
                )
            }
        }
    ) { padding ->
        when (selectedTabIndex) {
            0 -> Pet(navController, petViewModel, authViewModel)
            1 ->  PetList(petViewModel, authViewModel)
        }
    }
}