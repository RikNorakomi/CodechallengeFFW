package com.vanvelzen.codechallengeffw.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.vanvelzen.codechallengeffw.database.StarWarsDatabase

actual class DatabaseDriverFactory() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema = StarWarsDatabase.Schema, name = "starwars.db")
    }
}