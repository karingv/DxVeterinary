package com.karin.appveterinaria.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.karin.appveterinaria.model.Post
import java.util.*

class PostRepository {
    private val db = FirebaseFirestore.getInstance()
    private val postsCollection = db.collection("posts")
    private val storageRef = FirebaseStorage.getInstance("gs://appveterinaria-b12a3.appspot.com").reference // Actualiza con tu URL de Firebase Storage

    fun getPosts(callback: (List<Post>) -> Unit) {
        postsCollection.orderBy("timestamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { result ->
                val posts = result.map { document ->
                    document.toObject(Post::class.java)
                }
                callback(posts)
            }
            .addOnFailureListener { exception ->
                Log.w("PostRepository", "Error getting documents: ", exception)
                callback(emptyList())
            }
    }

    // Función para subir la imagen a Firebase Storage y luego agregar el post a Firestore
    fun addPost(post: Post, image: Uri?, callback: (Boolean) -> Unit) {
        if (image != null) {
            val imageRef = storageRef.child("images/${UUID.randomUUID()}") // Cambia "images/" según tu estructura de almacenamiento

            val uploadTask = imageRef.putFile(image)

            uploadTask.addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val postMap = mapOf(
                        "Titulo" to post.Titulo,
                        "content" to post.content,
                        "userID" to post.userID,
                        "userName" to post.userName,
                        "timestamp" to post.timestamp,
                        "image" to downloadUri.toString() // Guardar la URL de descarga de la imagen
                    )

                    postsCollection
                        .add(postMap)
                        .addOnSuccessListener { documentReference ->
                            Log.d("PostRepository", "DocumentSnapshot added with ID: ${documentReference.id}")
                            callback(true)
                        }
                        .addOnFailureListener { e ->
                            Log.w("PostRepository", "Error adding document", e)
                            callback(false)
                        }
                }.addOnFailureListener { e ->
                    Log.w("PostRepository", "Error getting download URL", e)
                    callback(false)
                }
            }.addOnFailureListener { e ->
                Log.e("PostRepository", "Error uploading image", e)
                callback(false)
            }
        } else {
            // Si image es nulo, puedes manejarlo según tus necesidades
            // Aquí estás subiendo el post sin imagen
            val postMap = mapOf(
                "Titulo" to post.Titulo,
                "content" to post.content,
                "userID" to post.userID,
                "userName" to post.userName,
                "timestamp" to post.timestamp,
                "image" to "" // Puedes dejarlo vacío o manejarlo según tus necesidades
            )

            postsCollection
                .add(postMap)
                .addOnSuccessListener { documentReference ->
                    Log.d("PostRepository", "DocumentSnapshot added with ID: ${documentReference.id}")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    Log.w("PostRepository", "Error adding document", e)
                    callback(false)
                }
        }
    }
}