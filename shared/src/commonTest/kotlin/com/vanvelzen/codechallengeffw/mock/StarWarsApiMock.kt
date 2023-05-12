package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

class StarWarsApiMock : StarWarsApi {

    override suspend fun getPeople(page: Int): Response<PeopleResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonById(id: String): Response<StarWarsCharacter> {
        TODO("Not yet implemented")
    }
}