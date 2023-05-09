package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages

interface StarWarsWithImagesApi {
    suspend fun getAllCharacters(): List<PeopleWithImages>
    suspend fun getCharacterById(id: String): PeopleWithImages
}