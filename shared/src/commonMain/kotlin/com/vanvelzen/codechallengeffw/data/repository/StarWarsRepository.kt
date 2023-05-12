package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import com.vanvelzen.codechallengeffw.data.dto.toStarWarsCharacters
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class StarWarsRepository(
    private val starWarsApi: StarWarsApi,
    private val starWarsWithImagesApi: StarWarsWithImagesApi,
    private val log: Logger
) {

    internal data class LocalCacheMock (
        val cachedCharacters: MutableSet<StarWarsCharacter> = mutableSetOf(),
        var lastCachedPage: Int? = null,
        var hasNextPage: Boolean = true,
    )

    private val localCacheMock = LocalCacheMock()
    private val cachedCharactersWithImages = mutableListOf<PeopleWithImages>()

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getStarWarsCharacters(): Response<List<StarWarsCharacter>> {
        return withContext(Dispatchers.Main) {

            // If there is no next page to load on api return cached data
            with (localCacheMock){
                if (!hasNextPage){
                    return@withContext Response.Success(cachedCharacters.toList(), false)
                }
            }

            // Else determine which page to load
            val pageToLoad = (localCacheMock.lastCachedPage?.plus(1)) ?: StarWarsApi.FIRST_PAGE_ID
            log.v { "Fetching page: $pageToLoad" }

            val deferredList = listOf(
                this.async { starWarsApi.getPeople(pageToLoad) },
                this.async { getCharactersWithImageUrl() },
            )

            deferredList.awaitAll() // wait for all data to be processed without blocking the UI thread

            val responseCharacterDetails = deferredList[0].getCompleted()
            val responseImages = deferredList[1].getCompleted()

            // If swapi api response with error return with Error response
            if (responseCharacterDetails is Response.Error) {
                val errorMsg = "Issue retrieving Star Wars character info from Swapi."
                log.e { errorMsg }
                return@withContext Response.Error(errorMessage = errorMsg)
            }
            val charactersResponse = (responseCharacterDetails as Response.Success).data as PeopleResponse
            var characters = charactersResponse.toStarWarsCharacters()

            // When both calls return successfully add imageUrl to data
            if (responseImages is Response.Success) {
                val imagedCharacters = responseImages.data as List<PeopleWithImages>

                characters = characters.onEach { starWarsCharacter ->
                    starWarsCharacter.imageUrl = imagedCharacters
                        .firstOrNull {
                            (it.name.equals(
                                starWarsCharacter.name,
                                ignoreCase = true
                            ))
                        }
                        ?.imageUrl
                    log.v { "For: ${starWarsCharacter.name}: Added imageUrl: ${starWarsCharacter.imageUrl}" }
                }
            } else log.e { "Getting image urls from the Star Wars api with images failed!" }

            with (localCacheMock){
                cachedCharacters.addAll(characters)
                lastCachedPage = pageToLoad
                hasNextPage = !charactersResponse.next.isNullOrEmpty()
                log.v { "Local cache has ${cachedCharacters.size} characters. hasNextPage:${hasNextPage}" }
            }

            return@withContext Response.Success(localCacheMock.cachedCharacters.toList(), localCacheMock.hasNextPage)
        }
    }

    suspend fun getCharacterDetails(id: String): Response<StarWarsCharacter> {
        val cachedCharacter = localCacheMock.cachedCharacters.firstOrNull { it.id == id }
        if (cachedCharacter == null) {
            log.e { "Unable to find character with id: $id in cache. Should never happen!" }
        } else return Response.Success(cachedCharacter)

        return starWarsApi.getPersonById(id)
    }

    private suspend fun getCharactersWithImageUrl(): Response<List<PeopleWithImages>> =
        if (cachedCharactersWithImages.isNotEmpty()) {
            log.v { "Returning a cached response for getCharactersWithImageUrl() for ${cachedCharactersWithImages.size} characters." }
            Response.Success(cachedCharactersWithImages)
        } else {
            val response = starWarsWithImagesApi.getAllCharacters()
            if (response is Response.Success){
                if (response.data.isNotEmpty()){
                    cachedCharactersWithImages.addAll(response.data)
                }
            }
            response
        }


    suspend fun getCharacterDetailsById(id: String): Response<PeopleWithImages> =
        starWarsWithImagesApi.getCharacterById(id)

}

