package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse

interface StarWarsApi {
    suspend fun getPeople(): PeopleResponse

    suspend fun getPersonById(id: String): People
}