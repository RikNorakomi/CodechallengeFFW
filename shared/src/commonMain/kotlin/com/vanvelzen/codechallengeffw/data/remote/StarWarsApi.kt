package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.dto.PeopleDto
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

interface StarWarsApi {
    suspend fun getPeople(page: Int = FIRST_PAGE_ID): Response<PeopleResponse>
    suspend fun getPersonById(id: String): Response<PeopleDto>

    companion object {
        const val FIRST_PAGE_ID = 1
    }
}