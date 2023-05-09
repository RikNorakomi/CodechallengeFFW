package com.vanvelzen.codechallengeffw.data.api

import co.touchlab.kermit.Severity
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import com.vanvelzen.codechallengeffw.data.dto.toStarWarsCharacter
import com.vanvelzen.codechallengeffw.data.dto.toStarWarsCharacters
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
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
        const val BASE_URL = "https://rawcdn.githack.com/"
        const val PATH_TO_API = "akabab/starwars-api/0.2.1/api"
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


    private fun HttpRequestBuilder.people(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    override suspend fun getAllCharacters(): Response<List<StarWarsCharacter>> {
        return withContext(ioDispatcher) {
            log.d { "Fetching all characters." }
            try {

                val response: Array<PeopleWithImages> = client
                    .get { people("${PATH_TO_API}/all.json") }
                    .body()

                Response.Success(response.toStarWarsCharacters())
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }




    override suspend fun getCharacterById(id: String): Response<StarWarsCharacter> {
        return withContext(ioDispatcher) {
            log.d { "Fetching character details for person with id:$id" }
            try {
                val response: PeopleWithImages = client
                    .get { people("${PATH_TO_API}/${id}.json") }
                    .body()

                Response.Success(response.toStarWarsCharacter())
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }
}

internal inline fun <reified R : Any> String.convertToDataClass() =
    Json {
        ignoreUnknownKeys = true
    }.decodeFromString<R>(this)


