package com.vanvelzen.codechallengeffw.data.sdk

import com.vanvelzen.codechallengeffw.data.dto.toPeopleEntityList
import com.vanvelzen.codechallengeffw.data.local.Database
import com.vanvelzen.codechallengeffw.data.local.toPeople
import com.vanvelzen.codechallengeffw.data.local.toPeopleList
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi
import co.touchlab.kermit.Logger

interface SwapiSDK {
    suspend fun getPeople(forceReload: Boolean = false): Response<List<People>>
    suspend fun getPersonById(id: String): Response<People>
}

class SwapiSDKImpl(
    private val database: Database,
    private val api: StarWarsApi,
    private val log: Logger
) : SwapiSDK {

    private var lastCachedPage: Int? = null

    override suspend fun getPeople(forceReload: Boolean): Response<List<People>> {
        if (forceReload){
            database.clearDatabase()
            lastCachedPage = null
            log.v { "Clearing database & starting page loading at $FIRST_PAGE_ID" }
        }

        val pageId = lastCachedPage?.plus(1) ?: FIRST_PAGE_ID
        log.v { "Fetching page: $pageId" }

        return try {
            val cachedPeople = database.getAllCharacters()
            val isPageCached: Boolean = cachedPeople.firstOrNull { it.pageId == pageId.toLong() } != null
            return if (!forceReload && isPageCached) {
                lastCachedPage = pageId
                log.v { "Returning database cached getPeople() result for page: $pageId" }
                Response.Success(data = cachedPeople.toPeopleList())
            } else {
                val response = api.getPeople(pageId)
                if (response is Response.Error) {
                    log.e { "Issue getting people with pageId:$pageId from SWAPI: Error message=${response.errorMessage}" }
                    return response
                }
                with(response as Response.Success) {
                    database.createCharacters(this.data.toPeopleEntityList(pageId))
                    lastCachedPage = pageId
                    log.v { "Returning api results for getPeople() for page: $pageId" }
                    Response.Success(
                        data = database.getAllCharacters().toPeopleList(),
                        canLoadMore = !this.data.next.isNullOrEmpty()
                    )
                }
            }
        } catch (e: Exception) {
            log.e { "Issue getting people with pageId:$pageId: Exception=${e}" }
            Response.Error("Issue getting people from SwapiSDK. Exception: $e")
        }
    }

    override suspend fun getPersonById(id: String): Response<People> {
        return try {
            Response.Success(data = database.getCharacter(id).toPeople())
        } catch (e: Exception) {
            Response.Error("Issue getting people with id:$id from SwapiSDK. Exception: $e")
        }
    }

    companion object {
        const val FIRST_PAGE_ID = 1
    }
}