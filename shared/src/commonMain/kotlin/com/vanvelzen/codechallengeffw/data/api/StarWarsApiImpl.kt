package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger

/**
 * Client for the https://swapi.dev/ Star Wars api
 */
class StarWarsApiImpl(
    private val log: KermitLogger,
    engine: HttpClientEngine
) : StarWarsApi {

    private val client = HttpClient(engine) {
        expectSuccess = true

        install(Logging) {
            logger = object : KtorLogger {
                override fun log(message: String) {
                    // delegates Ktor logs to the Multiplatform KermitLogger solution
                    log.v { message }
                }
            }
            level = LogLevel.INFO
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
//
//    init {
//        ensureNeverFrozen()
//    }


    private fun HttpRequestBuilder.people(path: String) {
        url {
            takeFrom("https://swapi.dev/")
            encodedPath = path
        }
    }

    override suspend fun getPeople(): PeopleResponse {
        log.d { "Fetching People (Star Wars characters) from network" }
        return client.get {
            people("people")
        }.body()
    }
}
