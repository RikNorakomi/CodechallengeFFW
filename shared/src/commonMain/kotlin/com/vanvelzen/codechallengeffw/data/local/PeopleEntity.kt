package com.vanvelzen.codechallengeffw.data.local

import com.vanvelzen.codechallengeffw.data.sdk.People
import kotlinx.serialization.Serializable

@Serializable
class PeopleEntity(
    val id: String,
    val name: String,
    val height: String,
    val homeWorld: String,
    val gender: String,
    val mass: String,
    val skinColor: String,
    val hairColor: String,
    val birthYear: String,
    val eyeColor: String,
    val imageUrl: String?,
    val pageId: Long,
)

fun List<PeopleEntity>.toPeopleList() : List<People> {
    return this.map {
        it.toPeople()
    }
}

fun PeopleEntity.toPeople(): People {
    return People(
        id = id,
        name = name,
        height = height,
        homeWorld = homeWorld,
        gender = gender,
        mass = mass,
        skinColor = skinColor,
        hairColor = hairColor,
        birthYear = birthYear,
        eyeColor = eyeColor,
        imageUrl = imageUrl,
    )
}

