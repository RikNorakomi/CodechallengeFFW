package com.vanvelzen.codechallengeffw.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PeopleWithImagesDto(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
)


