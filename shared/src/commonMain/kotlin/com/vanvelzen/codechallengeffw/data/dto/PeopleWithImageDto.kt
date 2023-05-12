package com.vanvelzen.codechallengeffw.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleWithImagesDto(
    val id: Int = 0,
    val name: String = "",
    @SerialName("image") val imageUrl: String = "",
)


