package com.vanvelzen.codechallengeffw.models

import kotlinx.serialization.Serializable

@Serializable
data class StarWarsCharacter(
    val id: String = "",
    val name: String = "",
    val height: String = "",
    val homeWorld: String = "",
    val gender: String = "",
    val mass: String = "",
    val skinColor: String = "",
    val hairColor: String = "",
    val birthYear: String = "",
    val eyeColor: String = "",
    val films: List<String> = emptyList(),
    var imageUrl: String? = null,
)
