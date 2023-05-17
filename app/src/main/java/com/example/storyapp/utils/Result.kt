package com.example.storyapp.utils

sealed class Result<out R> private constructor(){
    data class Success<out T>(val data: T) : com.example.storyapp.utils.Result<T>()
    data class Error(val error: String) : com.example.storyapp.utils.Result<Nothing>()
    object Loading : com.example.storyapp.utils.Result<Nothing>()
}