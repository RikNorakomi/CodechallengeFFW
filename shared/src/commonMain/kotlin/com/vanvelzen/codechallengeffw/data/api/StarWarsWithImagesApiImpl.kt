package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger

/**
 * Client for the https://rawcdn.githack.com/akabab/starwars-api/0.2.1/api Star Wars api
 * In comparison to the Swapi api this api provides image urls for all the Star Wars characters
 */
class StarWarsWithImagesApiImpl(
    private val log: KermitLogger,
    engine: HttpClientEngine,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) : StarWarsWithImagesApi {

    companion object {
        const val BASE_URL = "https://rawcdn.githack.com/"
        const val PATH_TO_API = "akabab/starwars-api/0.2.1/api"
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val client = HttpClient(engine) {
        expectSuccess = true

        install(Logging) {
            logger = object : KtorLogger {
                override fun log(message: String) {
                    // delegates Ktor logs to the Multiplatform KermitLogger solution
                    log.v { message }
                }
            }
            level = LogLevel.ALL
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

                val stringBody: String = client
                    .get { people("${PATH_TO_API}/all.json") }
                    .body()

                val response = json.decodeFromString<List<PeopleWithImages>>(stringBody)

                Response.Success(response)
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
                val response: PeopleWithImages = client
                    .get { people("${PATH_TO_API}/${id}.json") }
                    .body()

                Response.Success(response)
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }
}



