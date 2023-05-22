package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.sdk.People
import com.vanvelzen.codechallengeffw.data.sdk.SwapiSDK

class SwapiSDKMock : SwapiSDK {

    private var nextResult: () -> Response<List<People>> = { error("Uninitialized!") }
    var calledCount = 0
        private set

    fun prepareResult(response: Response<List<People>>) {
        nextResult = { response }
    }


    override suspend fun getPeople(forceReload: Boolean): Response<List<People>> {
        val result = nextResult()
        calledCount++
        return result
    }

    override suspend fun getPersonById(id: String): Response<People> {
        TODO("Not yet implemented")
    }

    fun successResponse(): Response<List<People>> {
        return Response.Success(data = listPeopleMock)
    }

    fun errorResponse(errorMessage: String = ""): Response<List<People>> {
        return Response.Error(errorMessage)
    }
}