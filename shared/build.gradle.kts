plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlinVersion // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.serialization
    id("com.android.library")
    id("com.google.devtools.ksp") version Versions.kotlinKSP // https://github.com/google/ksp/releases
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-7"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Koin) {
                    api(core)
                    api(test)
                }


                implementation(Kotlinx.serialization)
                implementation(kotlin("stdlib-common"))
                api(Logging.touchlabKermit)
                implementation(Multiplatform.kmm_viewmodel)

                implementation(Ktor.clientCore)
                implementation(Ktor.contentNegotaiotion)
                implementation(Ktor.logging)
                implementation(Ktor.jsonSerialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
//                implementation("app.cash.turbine:turbine:0.13.0")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Ktor.androidClient)
                implementation(AndroidX.lifecycleViewModel)
                implementation(AndroidX.lifecycleRuntime)
            }
        }

        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Ktor.iOSClient)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
}

android {
    namespace = "com.vanvelzen.codechallengeffw"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}