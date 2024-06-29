package com.karin.appveterinaria.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.play.integrity.internal.s
import com.karin.appveterinaria.R


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Pet,
        BottomNavItem.Descubre,
        BottomNavItem.Productos
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
//            .background(Color(0xFFFF0000))
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        backgroundColor = Color(0xFF212338),
        contentColor = Color.White
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        loadVectorResource(item.iconResource),
                        contentDescription = item.title,
                        //tint = Color.White,
                        tint = if (currentRoute == item.route) Color.White else Color.Gray.copy(0.4f),
                        modifier = Modifier.size(26.dp)
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray.copy(0.4f),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(var title: String, var iconResource: Int, var route: String) {
    object Home : BottomNavItem("Home", R.drawable.outline_home_24, "post") // home
    object Pet : BottomNavItem("Pet", R.drawable.baseline_pets_24, "addpet") // pet
    object Descubre : BottomNavItem("Descubre", R.drawable.outline_shopping_cart_24, "descubre") // descubre
    object Productos : BottomNavItem("Descubre", R.drawable.rounded_pet_supplies_24, "reservation")


}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Column(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = BottomNavItem.Home.route) {
            composable(BottomNavItem.Home.route) { /* Screen content for Home */ }
            composable(BottomNavItem.Pet.route) { /* Screen content for Pet */ }
            composable(BottomNavItem.Descubre.route) { /* Screen content for Descubre */ }
            composable(BottomNavItem.Productos.route) { /* Screen content for Descubre */ }
        }
        BottomNavigationBar(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    MainScreen()
}