package com.karin.appveterinaria.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.karin.appveterinaria.model.ProductModel
import com.karin.appveterinaria.viewmodel.CartViewModel
import com.karin.appveterinaria.viewmodel.ProductViewModel

@Composable
fun ProductList(
    navController: NavController,
    category: String,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    val products by productViewModel.products.collectAsState()

    LaunchedEffect(category) {
        Log.d("ProductList", "Loading products for category: $category")
        productViewModel.loadProductsByCategory(category)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Productos de ${category.capitalize()}", style = MaterialTheme.typography.h6)
        LazyColumn {
            items(products) { product ->
                ProductItem(product = product, onAddToCart = { cartViewModel.addItemToCart(it) })
            }

        }
        /*
        FloatingActionButton(onClick = {
            Log.d("ProductList", "Navigating to add_product/$category")
            navController.navigate("add_product/$category")
        }) {
            Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
        }
         */
    }
}

@Composable
fun ProductItem(product: ProductModel, onAddToCart: (ProductModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, style = MaterialTheme.typography.h6)
            Text(product.description)
            Text("Precio: S/.${product.price}")
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(150.dp)
            )
            Button(onClick = { onAddToCart(product) }) {
                Text("Agregar al carrito")
            }
        }
    }
}