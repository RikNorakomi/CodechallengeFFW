package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.PeopleDto
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi

class StarWarsApiMock : StarWarsApi {

    private var nextResult: () -> Response<PeopleResponse> = { error("Uninitialized!") }
    var calledCount = 0
        private set
    fun prepareResult(response: Response<PeopleResponse>) {
        nextResult = { response }


    }
    override suspend fun getPeople(page: Int): Response<PeopleResponse> {
        val result = nextResult()
        calledCount++
        return result
    }

    override suspend fun getPersonById(id: String): Response<PeopleDto> {
        TODO("Not yet implemented")
    }

    fun successResponse(): Response<PeopleResponse> {
        return Response.Success(data = peopleResponseMock)
    }

    fun errorResponse(errorMessage: String = ""): Response<PeopleResponse> {
        return Response.Error(errorMessage)
    }


}