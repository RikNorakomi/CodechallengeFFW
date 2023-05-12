package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto

class StarWarsWithImagesApiMock : StarWarsWithImagesApi {
    override suspend fun getAllCharacters(): Response<List<PeopleWithImagesDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterById(id: String): Response<PeopleWithImagesDto> {
        TODO("Not yet implemented")
    }

}