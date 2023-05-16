package com.vanvelzen.codechallengeffw.mock

import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.dto.PeopleResponse
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImagesDto

val listPeople = listOf<People>(
    People(name = "name1", height = "someheight1", homeworld = "homeWorld1"),
    People(name = "name2", height = "someheight2", homeworld = "homeWorld2"),
)

val peopleResponseMock = PeopleResponse(
    next = null,
    previous = null,
    count = listPeople.size,
    results = listPeople
)

val listPeopleWithImagesMock = listOf<PeopleWithImagesDto>(
    PeopleWithImagesDto(name = listPeople[0].name, id = 1, imageUrl = "someurl1"),
    PeopleWithImagesDto(name = listPeople[1].name, id = 2, imageUrl = "someurl2"),
)