plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.vanvelzen.codechallengeffw.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.vanvelzen.codechallengeffw.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.1")

    implementation("androidx.navigation:navigation-compose:2.6.0-beta01")
//    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
//    implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
//    implementation "io.ktor:ktor-client-core:1.6.3"
//    implementation "io.ktor:ktor-client-json:1.6.3"
//    implementation "io.ktor:ktor-client-serialization-jvm:1.6.3"

    implementation("io.coil-kt:coil:2.3.0")
    implementation("io.coil-kt:coil-compose:2.3.0")


//    implementation(Koin.core)
//    implementation(Koin.coreExtensions)
    implementation(Koin.androidCompose)
    implementation(Koin.android)
}