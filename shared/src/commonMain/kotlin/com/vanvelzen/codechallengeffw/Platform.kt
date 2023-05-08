package com.vanvelzen.codechallengeffw

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform