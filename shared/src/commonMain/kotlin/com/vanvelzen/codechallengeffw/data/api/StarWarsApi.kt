package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

interface StarWarsApi {
    suspend fun getPeople(): Response<List<StarWarsCharacter>>
    suspend fun getPersonById(id: String): Response<StarWarsCharacter>
}