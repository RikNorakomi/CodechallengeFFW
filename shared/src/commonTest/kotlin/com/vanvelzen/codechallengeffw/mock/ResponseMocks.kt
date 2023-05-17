package com.vanvelzen.codechallengeffw.mock

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import com.vanvelzen.codechallengeffw.data.remote.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

val contentTypeJsonHeader = headersOf(
    HttpHeaders.ContentType,
    ContentType.Application.Json.toString()
)

val mockEngineWithNotFoundResponse = MockEngine {
    respond(
        content = "",
        status = HttpStatusCode.NotFound
    )
}

val logger = Logger(
    config = object : LoggerConfig {
        override val logWriterList: List<LogWriter> = emptyList()
        override val minSeverity: Severity = Severity.Assert
    },
    tag = ""
)

fun mockKtorClientReturningNotFoundResponse(): HttpClient {
    val ktorClient = KtorClient(logger, mockEngineWithNotFoundResponse)
    return ktorClient.client
}