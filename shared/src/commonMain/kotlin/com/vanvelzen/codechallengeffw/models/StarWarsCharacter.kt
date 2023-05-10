package com.vanvelzen.codechallengeffw.models


data class StarWarsCharacter(
    val id: String = "",
    val name: String = "",
    val height: String = "",
    val homeWorld: String = "",
    val gender: String = "",
    val edited: String = "",
    val created: String = "",
    val mass: String = "",
    var imageUrl: String? = null,
    val films: List<String> = emptyList(),
    val species: List<String> = emptyList(),
    val vehicles: List<String> = emptyList(),
    val starships: List<String> = emptyList(),
    val skinColor: String = "",
    val hairColor: String = "",
    val birthYear: String = "",
    val eyeColor: String = "",
)
