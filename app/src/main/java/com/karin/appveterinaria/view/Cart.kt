package com.karin.appveterinaria.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.karin.appveterinaria.model.CartItemModel
import com.karin.appveterinaria.viewmodel.CartViewModel

@Composable
fun Cart(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Carrito de Compras", style = MaterialTheme.typography.h6)

        LazyColumn {
            items(cartItems) { cartItem ->
                CartItemRow(cartItem, cartViewModel::updateItemQuantity)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text("Total: S/.${String.format("%.2f", totalPrice)}", style = MaterialTheme.typography.h5)

        Button(onClick = {
            // Lógica para proceder con la compra
        }) {
            Text("Proceder con la Compra")
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItemModel, updateQuantity: (String, Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.productImage,
            contentDescription = cartItem.productName,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(cartItem.productName, style = MaterialTheme.typography.body1)
            Text("Precio: S/.${String.format("%.2f", cartItem.productPrice)}")
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { updateQuantity(cartItem.productId, cartItem.quantity - 1) }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Disminuir cantidad")
                }
                Text("${cartItem.quantity}", style = MaterialTheme.typography.body1)
                IconButton(onClick = { updateQuantity(cartItem.productId, cartItem.quantity + 1) }) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar cantidad")
                }
            }
        }
    }
}
