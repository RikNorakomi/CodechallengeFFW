object Versions {
    const val kermit = "2.0.0-RC4"
    const val koin = "3.2.0"
    const val koinAndroidXViewModel = "2.2.3" // Latest versions: https://mvnrepository.com/artifact/org.koin/koin-androidx-viewmodel?repo=jcenter
    const val kotlinxSerialization = "1.5.0"
    const val kotlinVersion = "1.8.21"
    const val kotlinKSP = "1.8.21-1.0.11" // https://github.com/google/ksp
    const val nativeCoroutines = "1.0.0-ALPHA-8"
    const val kmmViewmodel = "1.0.0-ALPHA-8" // https://github.com/rickclephas/KMM-ViewModel
    const val ktor = "2.3.0" // https://mvnrepository.com/artifact/io.ktor/ktor-client-core-jvm

    // Android only
    const val androidGradlePlugin = "8.0.1"
    const val androidX ="2.6.1"
    const val koinAndroid = "3.4.0"
    const val koinAndroidCompose = "3.4.4"
    const val kotlinCompilerExtensionVersion = "1.4.7" // // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
}

object Multiplatform {
    const val kmm_viewmodel = "com.rickclephas.kmm:kmm-viewmodel-core:${Versions.kmmViewmodel}" // For documentation: https://github.com/rickclephas/KMM-ViewModel
//    const val kmm_native_coroutines = "com.rickclephas.kmm:kmm-viewmodel-core:${Versions.kmm_viewmodel}" // For documentation: https://github.com/rickclephas/KMM-ViewModel

}

// Dependency Injection
object Koin {
    // Documentation at: https://insert-koin.io/docs/reference/koin-mp/kmp
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val coreExtensions = "io.insert-koin:koin-androidx-viewmodel:${Versions.koinAndroidXViewModel}"
    const val test = "io.insert-koin:koin-test:${Versions.koin}"

    const val androidCompose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
    const val android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
}

// Networking
object Ktor {

    // Documentation at: : https://ktor.io/docs/getting-started-ktor-client-multiplatform-mobile.html
    const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val contentNegotaiotion = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}" // https://ktor.io/docs/serialization.html
    const val jsonSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}" // https://ktor.io/docs/logging.html
    const val clientMock = "io.ktor:ktor-client-mock:${Versions.ktor}" // https://ktor.io/docs/logging.html


    // Platform specific
    const val androidClient = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val iOSClient = "io.ktor:ktor-client-darwin:${Versions.ktor}"
}

object Kotlinx {
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
}

object AndroidX {
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidX}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidX}"
}

object Logging {
    const val touchlabKermit = "co.touchlab:kermit:${Versions.kermit}" // For documentation: https://kermit.touchlab.co/docs/
}
