package com.vanvelzen.codechallengeffw.data.repository

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.Response
import com.vanvelzen.codechallengeffw.data.api.StarWarsApi
import com.vanvelzen.codechallengeffw.data.api.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class StarWarsRepository(
    private val starWarsApi: StarWarsApi,
    private val starWarsWithImagesApi: StarWarsWithImagesApi,
    log: Logger
) {

    private val log = log.withTag("StarWarsRepository")

    private var cachedCharacters = listOf<StarWarsCharacter>()

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getStarWarsCharacters(): Response<List<StarWarsCharacter>> {
        return withContext(Dispatchers.Main) {
            val deferredList = listOf(
                this.async { starWarsApi.getPeople() },
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
            var characters = (responseCharacterDetails as Response.Success).data as List<StarWarsCharacter>

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

            cachedCharacters = characters
            log.e { "First character url: ${characters.get(0).imageUrl}" }

            return@withContext Response.Success(characters)
        }
    }

    suspend fun getCharacterDetails(id: String): Response<StarWarsCharacter> {
        val cachedCharacter = cachedCharacters.firstOrNull() { it.id == id }
        if (cachedCharacter == null){
            log.e { "Unable to find character with id: $id in cache. Should never happen!" }
        } else return Response.Success(cachedCharacter)

        return starWarsApi.getPersonById(id)
    }

    private suspend fun getCharactersWithImageUrl(): Response<List<PeopleWithImages>> =
        starWarsWithImagesApi.getAllCharacters()

    suspend fun getCharacterDetailsById(id: String): Response<PeopleWithImages> =
        starWarsWithImagesApi.getCharacterById(id)

}

