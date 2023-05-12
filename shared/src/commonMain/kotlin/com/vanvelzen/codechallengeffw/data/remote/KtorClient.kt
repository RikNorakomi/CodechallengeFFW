package com.vanvelzen.codechallengeffw.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

class KtorClient(
    private val log: co.touchlab.kermit.Logger,
    engine: HttpClientEngine,
) {

    val client = HttpClient(engine) {
        expectSuccess = true

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    // delegates Ktor logs to the Multiplatform KermitLogger solution
                    log.v { message }
                }
            }
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json()
        }

        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }
}