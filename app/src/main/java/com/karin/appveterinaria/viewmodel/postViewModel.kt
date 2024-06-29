package com.karin.appveterinaria.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karin.appveterinaria.model.Post
import com.karin.appveterinaria.repository.PostRepository

class PostViewModel : ViewModel() {
    private val postRepository = PostRepository()
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts
    private val _addPostResult = MutableLiveData<Boolean>()
    val addPostResult: LiveData<Boolean> = _addPostResult

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        postRepository.getPosts { posts ->
            _posts.value = posts
        }
    }

    //    fun addPost(post: Post): LiveData<Boolean> {
//        val result = MutableLiveData<Boolean>()
//        postRepository.addPost(post) { success ->
//            result.value = success
//            _addPostResult.value = true
//        }
//        return result
//    }
    fun addPost(post: Post, image: Uri?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        postRepository.addPost(post, image) { success ->
            if (success) {
                _addPostResult.postValue(true)
            } else {
                _addPostResult.postValue(false)
            }
            result.postValue(success)
        }
        return result
    }



}