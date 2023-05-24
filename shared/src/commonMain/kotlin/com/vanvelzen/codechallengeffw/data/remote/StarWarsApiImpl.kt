package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.dto.PeopleDto
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import co.touchlab.kermit.Logger as KermitLogger

/**
 * Client for the https://swapi.dev/ Star Wars api
 */
class StarWarsApiImpl(
    private val log: KermitLogger,
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) : StarWarsApi {

    companion object {
        const val BASE_URL = "https://swapi.dev/"
    }

    private fun HttpRequestBuilder.people(path: String) {
        url {
            takeFrom(BASE_URL)
            encodedPath = path
        }
    }

    override suspend fun getPeople(page: Int): Response<PeopleResponse> {
        return withContext(ioDispatcher) {
            log.d { "Fetching all characters." }
            try {

                val response: PeopleResponse = client.get {
                    people("api/people/?page=${page}")
                }.body()

                Response.Success(response)
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }

    override suspend fun getPersonById(id: String): Response<PeopleDto> {
        return withContext(ioDispatcher) {
            log.d { "Fetching character details for person with id:$id" }
            try {
                val response: PeopleDto = client.get {
                    people("api/people/${id}")
                }.body()

                Response.Success(response)
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Response.Error(e.toString())
            }
        }
    }
}
