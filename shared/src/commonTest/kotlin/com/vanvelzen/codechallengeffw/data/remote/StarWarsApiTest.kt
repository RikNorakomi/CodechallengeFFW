package com.vanvelzen.codechallengeffw.data.remote

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import com.vanvelzen.codechallengeffw.data.dto.getID
import com.vanvelzen.codechallengeffw.data.dto.toStarWarsCharacter
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi.Companion.FIRST_PAGE_ID
import com.vanvelzen.codechallengeffw.mock.contentTypeJsonHeader
import com.vanvelzen.codechallengeffw.mock.getPersonById
import com.vanvelzen.codechallengeffw.mock.listPeopleMock
import com.vanvelzen.codechallengeffw.mock.logger
import com.vanvelzen.codechallengeffw.mock.mockKtorClientReturningNotFoundResponse
import com.vanvelzen.codechallengeffw.mock.peopleResponseMock
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class StarWarsApiTest {

    @Test
    fun `Get People_success`() = runTest {
        val engine = MockEngine {
            assertEquals("https://swapi.dev/api/people/?page=${FIRST_PAGE_ID}", it.url.toString())
            respond(
                content = """{
                    "next":null,
                    "previous":null,
                    "count":${listPeopleMock.size},
                    "results": ${Json.encodeToString(listPeopleMock)}
                    }""",
                headers = contentTypeJsonHeader
            )
        }

        val ktor = KtorClient(logger, engine)
        val starWarsApi = StarWarsApiImpl(logger, ktor.client)

        val result = starWarsApi.getPeople(page = StarWarsApi.FIRST_PAGE_ID)
        assertEquals(Response.Success(data = peopleResponseMock), result)
    }

    @Test
    fun `Get People_failure`() = runTest {
        val starWarsApi = StarWarsApiImpl(logger, mockKtorClientReturningNotFoundResponse())
        val result = starWarsApi.getPeople(page = StarWarsApi.FIRST_PAGE_ID)
        assertEquals((result is Response.Error), true)
    }

    @Test
    fun `Get Person by Id_success`() = runTest {
        val validId = listPeopleMock[0].getID()
        val apiResponse = listPeopleMock.getPersonById(validId)
        val engine = MockEngine {
            assertEquals("https://swapi.dev/api/people/${validId}", it.url.toString())
            respond(
                content = """
                    ${Json.encodeToString(apiResponse)}
                    """,
                headers = contentTypeJsonHeader
            )
        }

        val ktor = KtorClient(logger, engine)
        val starWarsApi = StarWarsApiImpl(logger, ktor.client)

        val result = starWarsApi.getPersonById(id = validId)
        assertEquals(Response.Success(data = apiResponse!!.toStarWarsCharacter()), result)
    }

    @Test
    fun `Get Person by Id_failure`() = runTest {
        val validId = listPeopleMock[0].getID()
        val starWarsApi = StarWarsApiImpl(logger, mockKtorClientReturningNotFoundResponse())

        val result = starWarsApi.getPersonById(id = validId)
        assertEquals((result is Response.Error), true)
    }
}