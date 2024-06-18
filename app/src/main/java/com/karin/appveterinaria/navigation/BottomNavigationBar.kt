package com.karin.appveterinaria.navigation

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.karin.appveterinaria.R




@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Pet,
        BottomNavItem.Descubre
    )

    BottomNavigation(modifier = Modifier) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(loadVectorResource(item.iconResource), contentDescription = item.title) },
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
    object Home : BottomNavItem("Home", R.drawable.hogar, "home")
    object Pet : BottomNavItem("Pet", R.drawable.pata, "pet")
    object Descubre : BottomNavItem("Descubre", R.drawable.corazon, "descubre")
}