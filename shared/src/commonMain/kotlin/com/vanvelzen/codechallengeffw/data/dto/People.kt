package com.vanvelzen.codechallengeffw.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.log

@Serializable
data class People(
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

const val INVALID_CHARACTER_ID = "-1"
fun People.getID(): String {
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

