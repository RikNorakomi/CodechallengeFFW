package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.sdk.People
import com.vanvelzen.codechallengeffw.data.sdk.SwapiSDK
import com.vanvelzen.codechallengeffw.data.sdk.toStarWarsCharacter
import com.vanvelzen.codechallengeffw.data.sdk.toStarWarsCharacters
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class StarWarsRepository(
    private val swapiSDK: SwapiSDK,
    private val starWarsWithImagesApi: StarWarsWithImagesApi,
    private val log: Logger,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val cachedCharactersWithImages = mutableListOf<PeopleWithImagesDto>()

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getStarWarsCharacters(forceReload: Boolean = false): Response<List<StarWarsCharacter>> {
        return withContext(ioDispatcher) {

            val deferredList = listOf(
                this.async { swapiSDK.getPeople(forceReload) },
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
            val charactersResponse = (responseCharacterDetails as Response.Success).data as List<People>
            var characters = charactersResponse.toStarWarsCharacters()

            // When both calls return successfully add imageUrl to data
            if (responseImages is Response.Success) {
                val imagedCharacters = responseImages.data as List<PeopleWithImagesDto>

                characters = characters.onEach { starWarsCharacter ->
                    starWarsCharacter.imageUrl = imagedCharacters
                        .firstOrNull {
                            (it.name.equals(
                                starWarsCharacter.name,
                                ignoreCase = true
                            ))
                        }
                        ?.image
                    log.v { "For: ${starWarsCharacter.name}: Added imageUrl: ${starWarsCharacter.imageUrl}" }
                }
            } else log.e { "Getting image urls from the Star Wars api with images failed!" }

            return@withContext Response.Success(characters.toList(), responseCharacterDetails.canLoadMore)
        }
    }

    suspend fun getCharacterDetails(id: String): Response<StarWarsCharacter> {
        val response = swapiSDK.getPersonById(id)
        if (response is Response.Error) return response

        // Add image url from Image APIs cached response
        val starWarsCharacter = (response as Response.Success).data.toStarWarsCharacter()
        starWarsCharacter.imageUrl = cachedCharactersWithImages.firstOrNull { it.id.toString() == id }?.image

        return Response.Success(starWarsCharacter)
    }

    private suspend fun getCharactersWithImageUrl(): Response<List<PeopleWithImagesDto>> =
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


    suspend fun getCharacterDetailsById(id: String): Response<PeopleWithImagesDto> =
        starWarsWithImagesApi.getCharacterById(id)

}

