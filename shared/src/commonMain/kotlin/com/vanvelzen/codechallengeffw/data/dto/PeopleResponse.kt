package com.vanvelzen.codechallengeffw.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val next: String? = "",
    val previous: String? = null,
    val count: Int = 0,
    val results: List<People> = emptyList()
)