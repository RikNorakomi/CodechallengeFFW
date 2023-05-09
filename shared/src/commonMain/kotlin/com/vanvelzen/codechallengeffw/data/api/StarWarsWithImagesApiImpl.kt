package com.vanvelzen.codechallengeffw.data.api

import co.touchlab.kermit.Severity
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger


/**
 * Client for the https://swapi.dev/ Star Wars api
 */
class StarWarsWithImagesApiImpl(
    private val log: KermitLogger,
    engine: HttpClientEngine,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) : StarWarsWithImagesApi {

    companion object {
        const val BASE_URL = "https://rawcdn.githack.com/akabab/starwars-api/0.2.1/api/"
    }

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


    private fun HttpRequestBuilder.people(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    override suspend fun getAllCharacters(): Response<List<PeopleWithImages>> {
        return withContext(ioDispatcher) {
            log.d { "Fetching all characters." }
            try {
                Response.Success(client.get {people("all.json") }.body())
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }




    override suspend fun getCharacterById(id: String): Response<PeopleWithImages> {
        return withContext(ioDispatcher) {
            log.d { "Fetching character details for person with id:$id" }
            try {
                Response.Success(client.get {people("${id}.json") }.body())
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }
}
