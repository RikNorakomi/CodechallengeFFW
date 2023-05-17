package com.vanvelzen.codechallengeffw.data.remote

import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApiImpl.Companion.BASE_URL
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApiImpl.Companion.PATH
import com.vanvelzen.codechallengeffw.mock.contentTypeJsonHeader
import com.vanvelzen.codechallengeffw.mock.listPeopleWithImagesMock
import com.vanvelzen.codechallengeffw.mock.logger
import com.vanvelzen.codechallengeffw.mock.mockKtorClientReturningNotFoundResponse
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class StarWarsWithImagesApiTest {

    @Test
    fun `Get all characters_success`() = runTest {
        val engine = MockEngine {
            assertEquals("${BASE_URL + PATH}/all.json", it.url.toString())
            respond(
                content = """
                    ${Json.encodeToString(listPeopleWithImagesMock)}
                    """,
                headers = contentTypeJsonHeader
            )
        }

        val ktor = KtorClient(logger, engine)
        val starWarsApi = StarWarsWithImagesApiImpl(logger, ktor.client)

        val result = starWarsApi.getAllCharacters()
        assertEquals(Response.Success(data = listPeopleWithImagesMock), result)
    }

    @Test
    fun `Get all characters_failure`() = runTest {
        val starWarsApi = StarWarsWithImagesApiImpl(logger, mockKtorClientReturningNotFoundResponse())
        val result = starWarsApi.getAllCharacters()
        assertEquals((result is Response.Error), true)
    }

    @Test
    fun `Get character by ID_success`() = runTest {
        val expectedResponse =  listPeopleWithImagesMock[0]
        val validId = expectedResponse.id.toString()
        val engine = MockEngine {
            assertEquals("${BASE_URL + PATH}/${validId}.json", it.url.toString())
            respond(
                content = """
                    ${Json.encodeToString(expectedResponse)}
                    """,
                headers = contentTypeJsonHeader
            )
        }

        val ktor = KtorClient(logger, engine)
        val starWarsApi = StarWarsWithImagesApiImpl(logger, ktor.client)


        val result = starWarsApi.getCharacterById(validId)
        assertEquals(Response.Success(data = expectedResponse), result)
    }

    @Test
    fun `Get character by ID_failure`() = runTest {
        val expectedResponse =  listPeopleWithImagesMock[0]
        val validId = expectedResponse.id.toString()
        val starWarsApi = StarWarsWithImagesApiImpl(logger, mockKtorClientReturningNotFoundResponse())
        val result = starWarsApi.getCharacterById(validId)
        assertEquals((result is Response.Error), true)
    }
}