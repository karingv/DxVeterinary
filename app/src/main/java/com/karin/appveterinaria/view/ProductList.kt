package com.karin.appveterinaria.view

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.android.play.integrity.internal.c
import com.karin.appveterinaria.model.ProductModel
import com.karin.appveterinaria.viewmodel.CartViewModel
import com.karin.appveterinaria.viewmodel.ProductViewModel

@Composable
fun ProductList(
    navController: NavController,
    category: String,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
) {
    val products by productViewModel.products.collectAsState()

    LaunchedEffect(category) {
        Log.d("ProductList", "Loading products for category: $category")
        productViewModel.loadProductsByCategory(category)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFeaedf4)).padding(top = 20.dp, bottom = 70.dp),

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
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),

    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(15.dp)) {
            Box(modifier = Modifier) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.size(150.dp)
                )
            }


            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier) {
                    Text(product.name, style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(product.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(
                        "Precio: S/.${product.price}",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(15.dp))
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp),
                        onClick = { onAddToCart(product) }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "plus",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Black
                        )
                        Text("Agregar a al lista", modifier = Modifier.fillMaxWidth(), color = Color.Black)
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    // Creamos un producto de prueba
    val product = ProductModel(
        id = "1",
        name = "Producto de prueba",
        description = "Descripción de prueba",
        price = 10.0,
        imageUrl = "https://example.com/image.jpg",
        category = "variados"
    )

    // Mostramos el producto de prueba en una tarjeta de producto
    ProductItem(product = product, onAddToCart = { /* No hacemos nada en la vista previa */ })
}