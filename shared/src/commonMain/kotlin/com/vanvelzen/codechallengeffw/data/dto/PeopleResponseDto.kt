package com.vanvelzen.codechallengeffw.data.dto

import com.vanvelzen.codechallengeffw.data.local.PeopleEntity
import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val next: String? = "",
    val previous: String? = null,
    val count: Int = 0,
    val results: List<PeopleDto> = emptyList(),
)

fun PeopleResponse.toPeopleEntityList(pageId: Int): List<PeopleEntity> {
    return results.map { it.toPeopleEntity(pageId) }
}

fun PeopleDto.toPeopleEntity(pageId: Int): PeopleEntity {
    return PeopleEntity(
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
            pageId = pageId.toLong()
        )
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