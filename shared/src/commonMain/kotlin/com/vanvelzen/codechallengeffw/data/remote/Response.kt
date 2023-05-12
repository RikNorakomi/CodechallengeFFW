package com.vanvelzen.codechallengeffw.data.remote

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val errorMessage: String) : Response<Nothing>()
}