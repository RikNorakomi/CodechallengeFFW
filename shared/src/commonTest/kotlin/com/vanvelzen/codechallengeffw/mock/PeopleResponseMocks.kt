package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto
import com.vanvelzen.codechallengeffw.data.dto.getID

val listPeopleMock: List<People> = listOf<People>(
    People(name = "name1", height = "someheight1", homeworld = "homeWorld1", url = "https://swapi.dev/api/people/1/"),
    People(name = "name2", height = "someheight2", homeworld = "homeWorld2", url ="https://swapi.dev/api/people/2/"),
)

fun List<People>.getPersonById(id: String): People? {
   return listPeopleMock.firstOrNull() { it.getID() == id }
}

val peopleResponseMock = PeopleResponse(
    next = null,
    previous = null,
    count = listPeopleMock.size,
    results = listPeopleMock
)

val listPeopleWithImagesMock = listOf<PeopleWithImagesDto>(
    PeopleWithImagesDto(
        name = listPeopleMock[0].name,
        id = listPeopleMock[0].getID().toInt(),
        image = "someurl1"),
    PeopleWithImagesDto(
        name = listPeopleMock[1].name,
        id = listPeopleMock[1].getID().toInt(),
        image = "someurl2"),
)