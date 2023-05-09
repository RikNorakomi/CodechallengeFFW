package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.Response
import com.vanvelzen.codechallengeffw.data.api.StarWarsApi
import com.vanvelzen.codechallengeffw.data.api.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

class StarWarsRepository(
    private val starWarsApi: StarWarsApi,
    private val starWarsWithImagesApi: StarWarsWithImagesApi,
    log: Logger
){

    private val log = log.withTag("StarWarsRepository")

    @Deprecated("Swapi api doesn't provide character image url", replaceWith = ReplaceWith("StarWarsRepository.getAllCharacters()"))
    suspend fun getPeople(): List<People> {
        val peopleResponse = starWarsApi.getPeople()
        log.v { "starWarsApi people response count: ${peopleResponse.count}" }
        return peopleResponse.results
    }


    @Deprecated("Swapi api doesn't provide character image url", replaceWith = ReplaceWith("StarWarsRepository.getCharacterDetailsById()"))
    suspend fun getCharacterDetails(characterId: String): People {
        val response = starWarsApi.getPersonById(characterId)
        log.v { "starWarsApi character detail response: $response" }
        return response
    }

    suspend fun getAllCharacters(): Response<List<StarWarsCharacter>> = starWarsWithImagesApi.getAllCharacters()

    suspend fun getCharacterDetailsById(id: String): Response<StarWarsCharacter> = starWarsWithImagesApi.getCharacterById(id)

}

