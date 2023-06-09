package com.vanvelzen.codechallengeffw

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.vanvelzen.codechallengeffw.data.local.Database
import com.vanvelzen.codechallengeffw.data.remote.KtorClient
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApi
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApi
import com.vanvelzen.codechallengeffw.data.remote.StarWarsApiImpl
import com.vanvelzen.codechallengeffw.data.remote.StarWarsWithImagesApiImpl
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.data.sdk.SwapiSDK
import com.vanvelzen.codechallengeffw.data.sdk.SwapiSDKImpl
import io.ktor.client.HttpClient
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    // doOnStartup is a lambda which is implemented in Swift on iOS side
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

    val kermit = koin.get<Logger> { parametersOf(null) }
    // AppInfo is a Kotlin interface with separate Android and iOS implementations
    val appInfo = koin.get<AppInfo>()
    kermit.v { "App Id ${appInfo.appId}" }

    return koinApplication
}

private val coreModule = module {
    single<HttpClient> {
        KtorClient(
            get { parametersOf("KtorClient") },
            get()
        ).client
    }
    single<StarWarsApi> {
        StarWarsApiImpl(
            get { parametersOf("StarWarsApiImpl") },
            get()
        )
    }
    single<StarWarsWithImagesApi> {
        StarWarsWithImagesApiImpl(
            get { parametersOf("StarWarsWithImagesApiImpl") },
            get()
        )
    }

    // platformLogWriter() is a relatively simple config option, useful for local debugging. For production
    // uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
    // that would likely go into platformModule expect/actual. See https://github.com/touchlab/Kermit
    val baseLogger = Logger(
        config = StaticConfig(logWriterList = listOf(platformLogWriter())),
        "CodeChallengeFFW"
    )
    factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }

    single {
        Database(get())
    }

    single<SwapiSDK> {
        SwapiSDKImpl(
            get(),
            get(),
            get { parametersOf("SwapiSDKImpl") },
        )
    }

    single {
        StarWarsRepository(
            get(),
            get(),
            get { parametersOf("StarWarsRepository") },
        )
    }

}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String): Lazy<Logger> = inject { parametersOf(tag) }

expect val platformModule: Module
