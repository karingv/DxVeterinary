package com.karin.appveterinaria.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karin.appveterinaria.R

@Composable
fun Posst(navController: NavController) {
    Box(modifier = Modifier.padding(15.dp)) {
        Card(
            modifier = Modifier.background(
                color = Color(0xFFFBB031),
                shape = RoundedCornerShape(30.dp)
            ), backgroundColor = Color(0xFFf0f3fa),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                Row(modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImagePerfil()
                    Column(modifier = Modifier.padding(5.dp)) {
                        Text("Titulo", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        Text("Fecha")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.rounded_bookmark_24),
                        contentDescription = "Guardado",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                }
                Text("loremp ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec odio nec nisl tincidunt tincidunt")
                Spacer(modifier = Modifier.padding(5.dp))

                ImagePost()


            }

        }
    }
}

@Composable
fun ImagePost() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Imagen d peerfil",
        modifier = Modifier
            .size(400.dp)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Crop,

        )
}

@Composable
fun ImagePerfil() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Imagen de perfil",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
    )
}


//@Preview(showSystemUi = true)
//@Composable
//fun PostPreview() {
//    Post()
//}