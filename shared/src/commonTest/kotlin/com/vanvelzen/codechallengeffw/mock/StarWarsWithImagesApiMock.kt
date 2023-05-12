package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages

class StarWarsWithImagesApiMock : StarWarsWithImagesApi {
    override suspend fun getAllCharacters(): Response<List<PeopleWithImages>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterById(id: String): Response<PeopleWithImages> {
        TODO("Not yet implemented")
    }

}