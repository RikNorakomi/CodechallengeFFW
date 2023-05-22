package com.vanvelzen.codechallengeffw.data.local

import com.vanvelzen.codechallengeffw.database.StarWarsDatabase

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = StarWarsDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.starwarsQueries

    internal fun getAllCharacters(): List<PeopleEntity> {
        return dbQuery.selectAllCharacters().executeAsList().map {it.toPeopleEntity() }
    }

    internal fun getCharacter(id: String): PeopleEntity {
        return dbQuery.getCharacterById(id).executeAsOne().toPeopleEntity()
    }

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllCharacters()
        }
    }

    internal fun createCharacters(characters: List<PeopleEntity>) {
        dbQuery.transaction {
            characters.forEach { character ->
                insertCharacter(character)
            }
        }
    }

    private fun insertCharacter(character: PeopleEntity) {
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
                pageId = pageId
            )
        }
    }
    private fun database.PeopleEntity.toPeopleEntity(): PeopleEntity {
        return with(this) {
            PeopleEntity(
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
    }
}





