package com.vanvelzen.codechallengeffw.data.sdk

import com.vanvelzen.codechallengeffw.data.dto.toPeople
import com.vanvelzen.codechallengeffw.data.dto.toPeopleEntityList
import com.vanvelzen.codechallengeffw.data.local.Database
import com.vanvelzen.codechallengeffw.data.local.toPeople
import com.vanvelzen.codechallengeffw.data.local.toPeopleList
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi
import co.touchlab.kermit.Logger

class SwapiSDK (
    private val database: Database,
    private val api: StarWarsApi,
    private val log: Logger
) {

    private var lastCachedPage: Int? = null

    suspend fun getPeople(forceReload: Boolean = false): Response<List<People>> {
        val pageId = lastCachedPage?.plus(1) ?: FIRST_PAGE_ID
        log.v { "Fetching page: $pageId" }

        return try {
            val cachedPeople = database.getAllCharacters()
            val isPageCached: Boolean = cachedPeople.firstOrNull { it.pageId == pageId.toLong() } != null
            return if (!forceReload && isPageCached) {
                Response.Success(data = cachedPeople.toPeopleList())
            } else {
                val response = api.getPeople(pageId)
                if (response is Response.Error) {
                    log.e { "Issue getting people with pageId:$pageId from SWAPI: Error message=${response.errorMessage}"}
                    return response
                }
                with(response as Response.Success){
                    database.createCharacters(this.data.toPeopleEntityList(pageId))
                    Response.Success(data = database.getAllCharacters().toPeopleList(), canLoadMore = !this.data.next.isNullOrEmpty())
                }
            }
        } catch (e: Exception) {
            log.e { "Issue getting people with pageId:$pageId: Exception=${e}"}
            Response.Error("Issue getting people from SwapiSDK. Exception: $e")
        }
    }

    suspend fun getPersonById(id: String): Response<People> {
        return try {
            val cachedPeople = database.getCharacter(id)
            if (cachedPeople == null){
                val response = api.getPersonById(id)
                if (response is Response.Error) return Response.Error("Issue finding People with id:$id in database or via API.")
                if (response is Response.Success) return Response.Success(data = response.data.toPeople())
            }

            Response.Success(data = cachedPeople.toPeople())
        } catch (e: Exception) {
            Response.Error("Issue getting people with id:$id from SwapiSDK. Exception: $e")
        }
    }



    companion object {
        const val FIRST_PAGE_ID = 1
    }
}