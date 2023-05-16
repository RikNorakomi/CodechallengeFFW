package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto

class StarWarsWithImagesApiMock : StarWarsWithImagesApi {

    private var nextResult: () -> Response<List<PeopleWithImagesDto>> = { error("Uninitialized!") }
    var calledCount = 0
        private set

    fun prepareResult(response: Response<List<PeopleWithImagesDto>>) {
        nextResult = { response }


    }

    override suspend fun getAllCharacters(): Response<List<PeopleWithImagesDto>> {
        val result = nextResult()
        calledCount++
        return result
    }

    override suspend fun getCharacterById(id: String): Response<PeopleWithImagesDto> {
        TODO("Not yet implemented")
    }

    fun successResponse(): Response<List<PeopleWithImagesDto>> {
        return Response.Success(data = listPeopleWithImagesMock)
    }

    fun errorResponse(errorMessage: String = ""): Response<List<PeopleWithImagesDto>> {
        return Response.Error(errorMessage)
    }
}