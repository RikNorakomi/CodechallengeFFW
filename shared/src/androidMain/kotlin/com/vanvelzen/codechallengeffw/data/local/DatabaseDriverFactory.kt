package com.vanvelzen.codechallengeffw.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.vanvelzen.codechallengeffw.database.StarWarsDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(schema = StarWarsDatabase.Schema, context = context, name = "starwars.db")
    }
}