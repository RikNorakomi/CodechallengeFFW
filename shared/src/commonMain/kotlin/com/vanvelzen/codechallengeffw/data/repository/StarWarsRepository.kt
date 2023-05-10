package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.Response
import com.vanvelzen.codechallengeffw.data.api.StarWarsApi
import com.vanvelzen.codechallengeffw.data.api.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

class StarWarsRepository(
    private val starWarsApi: StarWarsApi,
    private val starWarsWithImagesApi: StarWarsWithImagesApi,
    log: Logger
){

    private val log = log.withTag("StarWarsRepository")

    suspend fun getPeople(): Response<List<StarWarsCharacter>>  = starWarsApi.getPeople()

    suspend fun getCharacterDetails(id: String): Response<StarWarsCharacter> =  starWarsApi.getPersonById(id)
    suspend fun getAllCharacters(): Response<List<PeopleWithImages>> = starWarsWithImagesApi.getAllCharacters()

    suspend fun getCharacterDetailsById(id: String): Response<PeopleWithImages> = starWarsWithImagesApi.getCharacterById(id)

}

