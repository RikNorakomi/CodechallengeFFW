package com.vanvelzen.codechallengeffw.data.dto

import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val next: String? = "",
    val previous: String? = null,
    val count: Int = 0,
    val results: List<People> = emptyList()
)

fun PeopleResponse.toStarWarsCharacters(): List<StarWarsCharacter> {
    val collection = arrayListOf<StarWarsCharacter>()
    this.results.forEach {collection.add(it.toStarWarsCharacter()) }
    return collection
}

fun People.toStarWarsCharacter(): StarWarsCharacter {
    return with(this) {
        StarWarsCharacter(
            id = this.getID(),
            name = name,
            height = height,
            homeWorld = homeworld,
            gender = gender,
            mass = mass,
            skinColor = skinColor,
            hairColor = hairColor,
            birthYear = birthYear,
            eyeColor = eyeColor,
        )
    }
}