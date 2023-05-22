plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(Versions.androidGradlePlugin).apply(false)
    id("com.android.library").version(Versions.androidGradlePlugin).apply(false)
    kotlin("android").version(Versions.kotlinVersion).apply(false)
    kotlin("multiplatform").version(Versions.kotlinVersion).apply(false)
}

buildscript {
    dependencies {
        classpath(Square.sqlDelightGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


