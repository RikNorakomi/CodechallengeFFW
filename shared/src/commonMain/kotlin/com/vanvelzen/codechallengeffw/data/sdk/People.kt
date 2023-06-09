package com.vanvelzen.codechallengeffw.data.sdk

import com.vanvelzen.codechallengeffw.models.StarWarsCharacter

class People(
    val id: String,
    val name: String,
    val height: String = "",
    val homeWorld: String = "",
    val gender: String = "",
    val mass: String = "",
    val skinColor: String = "",
    val hairColor: String = "",
    val birthYear: String = "",
    val eyeColor: String = "",
    val imageUrl: String? = null,
)

fun People.toStarWarsCharacter(): StarWarsCharacter {
    return StarWarsCharacter(
        id = id,
        name = name,
        height = height,
        homeWorld = homeWorld,
        gender = gender,
        mass = mass,
        skinColor = skinColor,
        hairColor = hairColor,
        birthYear = birthYear,
        eyeColor = eyeColor
    )
}