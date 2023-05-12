package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto
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
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger

/**
 * Client for the https://rawcdn.githack.com/akabab/starwars-api/0.2.1/api Star Wars api
 * In comparison to the Swapi api this api provides image urls for all the Star Wars characters.
 * See also: https://github.com/akabab/starwars-api
 */
class StarWarsWithImagesApiImpl(
    private val log: KermitLogger,
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) : StarWarsWithImagesApi {

    companion object {
        const val BASE_URL = "https://rawcdn.githack.com/"
        const val PATH_TO_API = "akabab/starwars-api/0.2.1/api"
    }

    private val json = Json { ignoreUnknownKeys = true }



    private fun HttpRequestBuilder.people(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    override suspend fun getAllCharacters(): Response<List<PeopleWithImagesDto>> {
        return withContext(ioDispatcher) {
            log.d { "Fetching all characters." }
            try {

                val stringBody: String = client
                    .get { people("${PATH_TO_API}/all.json") }
                    .body()

                val response = json.decodeFromString<List<PeopleWithImagesDto>>(stringBody)

                Response.Success(response)
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }

    override suspend fun getCharacterById(id: String): Response<PeopleWithImagesDto> {
        return withContext(ioDispatcher) {
            log.d { "Fetching character details for person with id:$id" }
            try {
                val response: PeopleWithImagesDto = client
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



