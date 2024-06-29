package com.karin.appveterinaria

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.karin.appveterinaria.navigation.BottomNavigationBar
import com.karin.appveterinaria.navigation.Navigator
import com.karin.appveterinaria.ui.theme.AppVeterinariaTheme
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.CartViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel
import com.karin.appveterinaria.viewmodel.ProductViewModel
import com.karin.appveterinaria.viewmodel.ReservationViewModel
import com.karin.appveterinaria.viewmodel.ServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val petViewModel: PetViewModel by viewModels()
    private val reservationViewModel: ReservationViewModel by viewModels()
    private val serviceViewModel: ServiceViewModel by viewModels()
    private val productViewModel : ProductViewModel by viewModels()
    private val cartViewModel : CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppVeterinariaTheme{
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    VeterinariaApp(authViewModel, petViewModel, reservationViewModel, serviceViewModel, productViewModel, cartViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VeterinariaApp(authViewModel: AuthViewModel, petViewModel: PetViewModel, reservationViewModel: ReservationViewModel, serviceViewModel: ServiceViewModel, productViewModel: ProductViewModel, cartViewModel: CartViewModel) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val authState = authViewModel.authState.collectAsState().value
    Scaffold(
        topBar = {
            if (authState != null) {
                TopAppBar(
                    modifier = Modifier,
                    backgroundColor = Color(0xFFFFFFFF), // 0xFF212338 - 0xFFFFFFFF
                    contentColor = Color.Black,
                    title = { Text("Dx Veterinary") },
                    actions = {

                        IconButton(onClick = {
                            navController.navigate("reservationHistory")
                        }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Historial de Reservas")
                        }
                        IconButton(onClick = {
                            navController.navigate("cart")
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Ver Carrito")
                        }
                        IconButton(onClick = {
                            scope.launch {
                                authViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            }
                        }) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                        }

                    }
                )
            }
        },
        bottomBar = {
            if (authState != null) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        Navigator(navController = navController, authViewModel = authViewModel, petViewModel = petViewModel, reservationViewModel = reservationViewModel, serviceViewModel = serviceViewModel, productViewModel = productViewModel, cartViewModel = cartViewModel)
    }
}
