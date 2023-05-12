package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto

interface StarWarsWithImagesApi {
    suspend fun getAllCharacters(): Response<List<PeopleWithImagesDto>>
    suspend fun getCharacterById(id: String): Response<PeopleWithImagesDto>
}