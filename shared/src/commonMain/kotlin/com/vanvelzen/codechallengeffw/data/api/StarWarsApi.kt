package com.vanvelzen.codechallengeffw.data.api

import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse

interface StarWarsApi {
    suspend fun getPeople(): PeopleResponse
}