package com.vanvelzen.codechallengeffw

import com.vanvelzen.codechallengeffw.data.local.DatabaseDriverFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single {
        OkHttp.create()
    }
    single {
        DatabaseDriverFactory(get())
    }
}