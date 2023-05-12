package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages

interface StarWarsWithImagesApi {
    suspend fun getAllCharacters(): Response<List<PeopleWithImages>>
    suspend fun getCharacterById(id: String): Response<PeopleWithImages>
}