package com.karin.appveterinaria.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.graphics.drawable.toBitmap

@Composable
fun loadVectorResource(id: Int): ImageVector {
    val context = LocalContext.current
    val drawable = context.getDrawable(id)
    return (drawable as? android.graphics.drawable.VectorDrawable)?.toBitmap()?.asImageBitmap()
        ?.let { ImageVector.vectorResource(id) }
        ?: throw IllegalArgumentException("Vector drawable resource not found.")
}