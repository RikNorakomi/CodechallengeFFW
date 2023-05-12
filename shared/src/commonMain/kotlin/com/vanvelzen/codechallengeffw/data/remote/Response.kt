package com.vanvelzen.codechallengeffw.data.remote

sealed class Response<out T> {
    data class Success<out T>(val data: T, val canLoadMore: Boolean = true) : Response<T>()
    data class Error(val errorMessage: String) : Response<Nothing>()
}