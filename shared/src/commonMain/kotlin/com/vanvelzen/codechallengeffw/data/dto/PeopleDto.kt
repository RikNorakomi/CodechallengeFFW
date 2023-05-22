package com.vanvelzen.codechallengeffw.data.dto

import com.vanvelzen.codechallengeffw.data.sdk.People
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleDto(
    val name: String = "",
    val height: String = "",
    val homeworld: String = "",
    val gender: String = "",
    val edited: String = "",
    val created: String = "",
    val mass: String = "",
    val url: String = "",
    val films: List<String> = emptyList(),
    val species: List<String> = emptyList(),
    val vehicles: List<String> = emptyList(),
    val starships: List<String> = emptyList(),
    @SerialName("skin_color") val skinColor: String = "",
    @SerialName("hair_color") val hairColor: String = "",
    @SerialName("birth_year") val birthYear: String = "",
    @SerialName("eye_color") val eyeColor: String = "",
)

fun PeopleDto.toPeople(): People{
    return with (this) {
        People(
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
            imageUrl = null
        )
    }
}

