package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.StarWarsApi
import com.vanvelzen.codechallengeffw.data.dto.People

class StarWarsRepository(
    private val starWarsApi: StarWarsApi,
    log: Logger
){

    private val log = log.withTag("StarWarsRepository")

    suspend fun getPeople(): List<People> {
        val peopleResponse = starWarsApi.getPeople()
        log.v { "starWarsApi people response count: ${peopleResponse.count}" }
        return peopleResponse.results
    }
}