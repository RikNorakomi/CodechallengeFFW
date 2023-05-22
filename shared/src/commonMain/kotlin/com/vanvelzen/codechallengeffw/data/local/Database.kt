package com.vanvelzen.codechallengeffw.data.local

import com.vanvelzen.codechallengeffw.database.StarWarsDatabase

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = StarWarsDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.starwarsQueries

    internal fun getAllCharacters(): List<PeopleEntity> {
        return dbQuery.selectAllCharacters(::mapCharacterSelecting).executeAsList()
    }

    internal fun getCharacter(id: String): PeopleEntity {
        return dbQuery.getCharacterById(id, ::mapCharacterSelecting).execute()
    }

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllCharacters()
        }
    }

    internal fun createCharacters(characters: List<PeopleEntity>, apiPageId: String) {
        dbQuery.transaction {
            characters.forEach { character ->
                insertCharacter(character, apiPageId)
            }
        }
    }

    // TODO: Left of at - https://kotlinlang.org/docs/multiplatform-mobile-ktor-sqldelight.html#build-an-sdk 

    private fun insertCharacter(character: PeopleEntity, apiPageId: String) {
        with(character) {
            dbQuery.insertCharacter(
                id = id,
                name = name,
                height = height,
                homeWorld = homeWorld,
                gender = gender,
                mass = mass,
                skinColor = skinColor,
                hairColor = hairColor,
                birthYear = birthYear,
                eyeColor = eyeColor,
                imageUrl = imageUrl,
                pageId = apiPageId
            )
        }
    }

    private fun mapCharacterSelecting(
        id: String,
        name: String,
        height: String,
        homeWorld: String,
        gender: String,
        mass: String,
        skinColor: String,
        hairColor: String,
        birthYear: String,
        eyeColor: String,
        imageUrl: String?,
        pageId: Int,
    ): PeopleEntity = PeopleEntity(
        id = id,
        name = name,
        height = height,
        homeWorld = homeWorld,
        gender = gender,
        mass = mass,
        skinColor = skinColor,
        hairColor = hairColor,
        birthYear = birthYear,
        eyeColor = eyeColor,
        imageUrl = imageUrl,
        pageId = pageId
    )

}

