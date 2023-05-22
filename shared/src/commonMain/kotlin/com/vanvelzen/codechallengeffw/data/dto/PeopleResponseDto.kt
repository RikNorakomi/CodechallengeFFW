package com.vanvelzen.codechallengeffw.data.dto

import com.vanvelzen.codechallengeffw.data.local.PeopleEntity
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val next: String? = "",
    val previous: String? = null,
    val count: Int = 0,
    val results: List<PeopleDto> = emptyList(),
    var pageId: Int,
)

fun PeopleResponse.toPeopleEntityList(): List<PeopleEntity> {
    val collection = arrayListOf<PeopleEntity>()
    this.results.forEach {
        collection.add(it.toPeopleEntity(this.pageId))
    }
    return collection
}

fun PeopleResponse.toStarWarsCharacters(): List<StarWarsCharacter> {
    val collection = arrayListOf<StarWarsCharacter>()
    this.results.forEach {collection.add(it.toStarWarsCharacter()) }
    return collection
}

fun PeopleDto.toStarWarsCharacter(): StarWarsCharacter {
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

fun PeopleDto.toPeopleEntity(pageId: Int): PeopleEntity {
    return with(this) {
        PeopleEntity(
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
            imageUrl = null,
            pageId = pageId
        )
    }
}

const val INVALID_CHARACTER_ID = "-1"
fun PeopleDto.getID(): String {
    val substring = try {
        var string = this.url
        if (url.last().toString() == "/") {
            string = url.dropLast(1)
        }
        string.substring(string.lastIndexOf("/") + 1, string.length)
    } catch (e: Exception) {
        INVALID_CHARACTER_ID
    }
    return substring
}