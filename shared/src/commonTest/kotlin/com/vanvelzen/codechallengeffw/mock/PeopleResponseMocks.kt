package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.PeopleDto
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto
import com.vanvelzen.codechallengeffw.data.dto.getID

val listPeopleMockDtos: List<PeopleDto> = listOf<PeopleDto>(
    PeopleDto(name = "name1", height = "someheight1", homeworld = "homeWorld1", url = "https://swapi.dev/api/people/1/"),
    PeopleDto(name = "name2", height = "someheight2", homeworld = "homeWorld2", url ="https://swapi.dev/api/people/2/"),
)

fun List<PeopleDto>.getPersonById(id: String): PeopleDto? {
   return listPeopleMockDtos.firstOrNull() { it.getID() == id }
}

val peopleResponseMock = PeopleResponse(
    next = null,
    previous = null,
    count = listPeopleMockDtos.size,
    results = listPeopleMockDtos
)

val listPeopleWithImagesMock = listOf<PeopleWithImagesDto>(
    PeopleWithImagesDto(
        name = listPeopleMockDtos[0].name,
        id = listPeopleMockDtos[0].getID().toInt(),
        image = "someurl1"),
    PeopleWithImagesDto(
        name = listPeopleMockDtos[1].name,
        id = listPeopleMockDtos[1].getID().toInt(),
        image = "someurl2"),
)