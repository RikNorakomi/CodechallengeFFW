package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.PeopleDto
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto
import com.vanvelzen.codechallengeffw.data.dto.getID
import com.vanvelzen.codechallengeffw.data.sdk.People

val listPeopleMockDTOs: List<PeopleDto> = listOf<PeopleDto>(
    PeopleDto(name = "name1", height = "someheight1", homeworld = "homeWorld1", url = "https://swapi.dev/api/people/1/"),
    PeopleDto(name = "name2", height = "someheight2", homeworld = "homeWorld2", url ="https://swapi.dev/api/people/2/"),
)

fun List<PeopleDto>.getPersonById(id: String): PeopleDto? {
   return listPeopleMockDTOs.firstOrNull() { it.getID() == id }
}

val listPeopleMock: List<People> = listPeopleMockDTOs.map {
    with(it) { People(getID(), name = name, height = height, imageUrl = url) }
}

val peopleResponseMock = PeopleResponse(
    next = null,
    previous = null,
    count = listPeopleMockDTOs.size,
    results = listPeopleMockDTOs
)

val listPeopleWithImagesMock = listOf<PeopleWithImagesDto>(
    PeopleWithImagesDto(
        name = listPeopleMockDTOs[0].name,
        id = listPeopleMockDTOs[0].getID().toInt(),
        image = "someurl1"),
    PeopleWithImagesDto(
        name = listPeopleMockDTOs[1].name,
        id = listPeopleMockDTOs[1].getID().toInt(),
        image = "someurl2"),
)