package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

interface StarWarsWithImagesApi {
    suspend fun getAllCharacters(): Response<List<StarWarsCharacter>>
    suspend fun getCharacterById(id: String): Response<StarWarsCharacter>
}