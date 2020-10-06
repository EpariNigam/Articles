package com.nigam.articles.api

sealed class AppResult<out T : Any> {
    data class Success<out T : Any>(val output: T) : AppResult<T>()
    object Loading : AppResult<Nothing>()
}