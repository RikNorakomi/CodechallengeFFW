package com.vanvelzen.codechallengeffw.data.sdk

import com.vanvelzen.codechallengeffw.data.dto.toPeople
import com.vanvelzen.codechallengeffw.data.dto.toPeopleEntityList
import com.vanvelzen.codechallengeffw.data.local.Database
import com.vanvelzen.codechallengeffw.data.local.toPeople
import com.vanvelzen.codechallengeffw.data.local.toPeopleList
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi

class SwapiSDK (
    private val database: Database,
    private val api: StarWarsApi,
) {

    suspend fun getPeople(pageId: Int = StarWarsApi.FIRST_PAGE_ID, forceReload: Boolean = false): Response<List<People>> {
        return try {
            val cachedPeople = database.getAllCharacters()
            return if (cachedPeople.isNotEmpty() && !forceReload) {
                Response.Success(data = cachedPeople.toPeopleList())
            } else {
                val response = api.getPeople(pageId)
                if (response is Response.Error) return response
                with(response as Response.Success){
                    database.createCharacters(this.data.toPeopleEntityList(), pageId.toString())
                    Response.Success(data = database.getAllCharacters().toPeopleList())
                }
            }
        } catch (e: Exception) {
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


}