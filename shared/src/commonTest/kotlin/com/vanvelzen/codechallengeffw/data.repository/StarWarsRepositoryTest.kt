package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.mock.StarWarsApiMock
import com.vanvelzen.codechallengeffw.mock.StarWarsWithImagesApiMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class StarWarsRepositoryTest {

    private var logger = Logger(StaticConfig())
    private val starWarsApiMock = StarWarsApiMock()
    private val starWarsWithImagesApiMock = StarWarsWithImagesApiMock()

    private val repository: StarWarsRepository =
        StarWarsRepository(starWarsApiMock, starWarsWithImagesApiMock, logger)

    @Test
    fun `Get StarWars Characters_both apis return success_response successful with available image urls`() = runTest {
        starWarsApiMock.prepareResult(starWarsApiMock.successResponse())
        starWarsWithImagesApiMock.prepareResult(starWarsWithImagesApiMock.successResponse())
        val response = repository.getStarWarsCharacters()
        assertEquals(response is Response.Success, actual = true)
        assertEquals((response as Response.Success).data[0].imageUrl.isNullOrEmpty(), actual = false)
    }

    @Test
    fun `Get StarWars Characters_only Swapi API returns success_response successful without available image urls`() = runTest {
        starWarsApiMock.prepareResult(starWarsApiMock.successResponse())
        starWarsWithImagesApiMock.prepareResult(starWarsWithImagesApiMock.errorResponse())
        val response = repository.getStarWarsCharacters()
        assertEquals(response is Response.Success, actual = true)
        assertEquals((response as Response.Success).data[0].imageUrl.isNullOrEmpty(), actual = true)
    }

    @Test
    fun `Get StarWars Characters_StarWarsApi returns error_response error`() = runTest {
        starWarsApiMock.prepareResult(starWarsApiMock.errorResponse())
        starWarsWithImagesApiMock.prepareResult(starWarsWithImagesApiMock.successResponse())
        val response = repository.getStarWarsCharacters()
        assertEquals(response is Response.Error, actual = true)
    }
}