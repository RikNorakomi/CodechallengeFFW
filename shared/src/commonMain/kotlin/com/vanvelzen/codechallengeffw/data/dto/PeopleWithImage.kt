package com.vanvelzen.codechallengeffw.data.dto

data class PeopleWithImages(
    val image: String = "",
    val homeworld: String = "",
    val gender: String = "",
    val cybernetics: String = "",
    val skinColor: String = "",
    val mass: Int = 0,
    val wiki: String = "",
    val born: Int = 0,
    val diedLocation: String = "",
    val affiliations: List<String>?,
    val masters: List<String>?,
    val died: Int = 0,
    val bornLocation: String = "",
    val eyeColor: String = "",
    val species: String = "",
    val name: String = "",
    val id: Int = 0,
    val hairColor: String = "",
    val apprentices: List<String>?,
    val height: Double = 0.0
)