package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import kotlinx.coroutines.flow.Flow

interface StarWarsWithImagesApi {
    suspend fun getAllCharacters(): Response<List<PeopleWithImages>>
    suspend fun getCharacterById(id: String): Response<PeopleWithImages>
}