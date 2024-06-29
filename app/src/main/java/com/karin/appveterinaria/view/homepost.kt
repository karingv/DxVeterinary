package com.karin.appveterinaria.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.Timestamp
import com.karin.appveterinaria.R
import com.karin.appveterinaria.model.Post
import com.karin.appveterinaria.viewmodel.PostViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homepost(
    navController: NavController,
    viewModel: PostViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    val posts by viewModel.posts.observeAsState(emptyList())
    val buttonColor = colorResource(id = R.color.teal_700)

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addpost")},
                modifier = Modifier.padding(bottom = 60.dp), backgroundColor = Color(0xFFAAC3FD)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFf1f4fb))
                    .padding(paddingValues) // Añade paddingValues para el Scaffold
                    .padding(bottom = 64.dp)
            ) {
                LazyColumn {
                    items(posts.size) { index ->
                        val post = posts[index]
                        PostItem(post)
                    }
                }

            }
        }
    )
}

@Composable
fun PostItem(post: Post) {
    val formattedDate = post.timestamp?.toDate()?.formatToDay()
//0xFFBBDEFB
    Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp,  bottom = 7.dp, top = 7.dp)) {
        androidx.compose.material3.Card(
            modifier = Modifier.background(
                color = Color(0xFFfdfdfd),
                shape = RoundedCornerShape(30.dp)
            ), //backgroundColor = Color(0xFFfdfdfd),

            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFfdfdfd),
                contentColor = Color(0xFF000000)
            )
        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImagePerfil(post)
                    Column(modifier = Modifier.padding(5.dp)) {
                        Text(post.userName, fontWeight = FontWeight.Bold)
                        Text(formattedDate ?: "")

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
                Text(post.Titulo)
                Text(post.content)
                Spacer(modifier = Modifier.padding(5.dp))

                ImagePost(post)
            }

        }

    }
}

@Composable
fun ImagePost(post: Post) {
    Image(
        painter = rememberImagePainter(post.image),
        contentDescription = "Imagen de post",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Mantener la relación de aspecto 1:1
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ImagePerfil(post: Post) {
    Image(
        painter = rememberImagePainter(R.drawable.avatar_cat),
        contentDescription = "Imagen de perfil",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
    )
}

fun Date.formatToDay(): String {
    val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
    return formatter.format(this)
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPostItem() {
    MaterialTheme {
        val post = Post(
            Titulo = "Primer comentario",
            content = "Mi experiencia en la veterinaria fue excelente...",
            image = "https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg",
            userID = "12345",
            userName = "John Doe",
            timestamp = Timestamp.now()
        )
        PostItem(post)
    }
}