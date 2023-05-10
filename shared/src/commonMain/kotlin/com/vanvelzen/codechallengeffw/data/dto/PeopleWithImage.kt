package com.vanvelzen.codechallengeffw.data.dto

import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleWithImages(
    val id: Int = 0,
    val name: String = "",
    @SerialName("image") val imageUrl: String = "",

//    val height: Double = 0.0,
//    val mass: Int = 0,
//    val gender: String = "",
//    val homeworld: String = "",
//    val wiki: String = "",
//    @SerialName("class") val clazz: String = "",
//    val sensorColor: String = "",
//    val platingColor: String = "",
//    val born: Int = 0,
//    val bornLocation: String = "",
//    val died: Int = 0,
//    val diedLocation: String = "",
//    val species: String = "",
//    val hairColor: String = "",
//    val eyeColor: String = "",
//    val skinColor: String = "",
//    val cybernetics: String = "",
//    val equipment: List<String>? = null,
//    val affiliations: List<String>? = null,
//    val masters: List<String>? = null,
//    val apprentices: List<String>? = null,
//    val formerAffiliations: List<String>? = null,
)

//fun Array<PeopleWithImages>.toStarWarsCharacters(): List<StarWarsCharacter> {
//    val collection = arrayListOf<StarWarsCharacter>()
//    this.forEach {collection.add(it.toStarWarsCharacter()) }
//    return collection
//}

//fun PeopleWithImages.toStarWarsCharacter(): StarWarsCharacter {
//    return with(this) {
//        StarWarsCharacter(
//            name = name,
//            height = height.toString(),
//            homeWorld = "homeworld",
//            gender = gender,
//            mass = mass.toString(),
//            imageUrl = image,
//            skinColor = skinColor,
//            hairColor = hairColor,
//            birthYear = "birthYear",
//            eyeColor = eyeColor,
//            id = id.toString()
//        )
//    }
//}

