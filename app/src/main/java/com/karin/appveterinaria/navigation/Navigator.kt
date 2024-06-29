package com.karin.appveterinaria.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karin.appveterinaria.view.AddPet
import com.karin.appveterinaria.view.AddPost
import com.karin.appveterinaria.view.Cart
import com.karin.appveterinaria.view.Descubre
import com.karin.appveterinaria.view.Home
import com.karin.appveterinaria.view.Login
import com.karin.appveterinaria.view.Pet
import com.karin.appveterinaria.view.Register
import com.karin.appveterinaria.view.ReservationHistory
import com.karin.appveterinaria.view.Reservation
import com.karin.appveterinaria.view.ProductList
import com.karin.appveterinaria.view.homepost
import com.karin.appveterinaria.viewmodel.AuthViewModel
import com.karin.appveterinaria.viewmodel.CartViewModel
import com.karin.appveterinaria.viewmodel.PetViewModel
import com.karin.appveterinaria.viewmodel.ProductViewModel
import com.karin.appveterinaria.viewmodel.ReservationViewModel
import com.karin.appveterinaria.viewmodel.ServiceViewModel

@Composable
fun Navigator(
    navController: NavHostController , //Recordar la navegacion  = rememberNavController()
    authViewModel: AuthViewModel,
    petViewModel: PetViewModel,
    reservationViewModel: ReservationViewModel,
    serviceViewModel: ServiceViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(navController = navController, authViewModel)
        }
        composable("register") {
            Register(navController = navController, authViewModel)
        }
        composable("home") {
            Home(navController = navController)
        }
        composable("pet") {
            Pet(navController = navController, petViewModel, authViewModel)
        }
        composable("descubre") {
            Descubre(navController = navController, productViewModel, cartViewModel)
        }
        composable("reservation") {
            Reservation(
                navController = navController,
                serviceViewModel,
                petViewModel,
                authViewModel,
                reservationViewModel
            )
        }
        composable("reservationHistory") {
            ReservationHistory(navController, authViewModel, reservationViewModel)
        }
        composable("products/variados") {
            Log.d("Navigation", "Navigating to products/variados")
            ProductList(navController, "variados", productViewModel, cartViewModel)
        }
        composable("products/medicina") {
            Log.d("Navigation", "Navigating to products/medicina")
            ProductList(navController, "medicina", productViewModel, cartViewModel)
        }
        composable("cart") {
            Cart(navController, cartViewModel = cartViewModel)
        }


        composable("post") {
            homepost(navController = navController)
        }

        composable("addpost") {
            AddPost(navController = navController)
        }
        composable("addpet") {
            AddPet(navController = navController, petViewModel, authViewModel)
        }
    }
}
