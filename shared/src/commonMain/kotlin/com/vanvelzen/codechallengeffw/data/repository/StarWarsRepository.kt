package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.Response
import com.vanvelzen.codechallengeffw.data.api.StarWarsApi
import com.vanvelzen.codechallengeffw.data.api.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages

class StarWarsRepository(
    private val starWarsApi: StarWarsApi,
    private val starWarsWithImagesApi: StarWarsWithImagesApi,
    log: Logger
){

    private val log = log.withTag("StarWarsRepository")

    suspend fun getPeople(): List<People> {
        val peopleResponse = starWarsApi.getPeople()
        log.v { "starWarsApi people response count: ${peopleResponse.count}" }
        return peopleResponse.results
    }

    suspend fun getAllCharacters(): Response<List<PeopleWithImages>> = starWarsWithImagesApi.getAllCharacters()

    suspend fun getCharacterDetails(characterId: String): People {
        val response = starWarsApi.getPersonById(characterId)
        log.v { "starWarsApi character detail response: $response" }
        return response
    }

}