package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.dto.PeopleDto
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

interface StarWarsApi {
    suspend fun getPeople(page: Int): Response<PeopleResponse>
    suspend fun getPersonById(id: String): Response<PeopleDto>
}