# Star Wars KMM app code challenge

[After indexing this app should be able to build & run as tested in Android Studio. Please contact me if not.]

This app is created as part of a KMM Code Challenge (see description below). 
Due to the unavailability of Apple hardware only the Android and commonMain side of the KMM project has been implemented, but where possible multiplatform solutions have been created so future sharing code with iOS would be easier.

## Swapi AND akabab/starwars APIs
2 APIs have been used for fetching Star Wars related data as the Swapi api doesn't contain any information on image url.
If found a second Star Wars "API" source that returned a list of the same Star Wars characters as Swapi but where there where image urls available for most of the characters.
The [StarWarsRepository] in commonMain contains an example of how to combine calls to both api.

## Local caching
Due to time constraints I was not able to implement a (shared) local storage/database implementation yet.
For now the [StarWarsRepository] makes use of a simple in memory cache to prevent unnecessary network requests.

## Pagination
Due to time constraints I have also opted for creating a custom simple paginating mechanism over trying to implement something like the Paging3 library [see StarWarsRepository & The OverviewScreen composable]

## Unit & UI tests
Unit testing partly done on the api and repository components. Not yet done for the ViewModel layer as I am looking into MocKMP for a multiplatform solution: https://github.com/kosi-libs/MocKMP

## Shared code / multiplatform solutions
- Shared ViewModel, Networking & Repository implementations are available in the [commonMain] package.
- Ktor has been used for a shared network implementation.
- kotlinx.serialization for multiplatform approach to de/serialization
- Touchlab's Kermit Logger used for multiplatform solution to logging

### Android specific code
- Compose UI & Routing are done from the Android package
- Coil is used for Image loading

# The Code challenge

### Overview
Create a Star Wars Themed Android app that fetches data from a webservice (e.g., https://swapi.dev/) and displays it using Jetpack Compose.
The app should be written in Kotlin and should showcase your rapid learning skills in both Jetpack Compose and Kotlin Multiplatform Mobile (KMM).
If you need to cut anything from the implementation or make any assumptions due to time constraints, please make a note and let us know later when presenting your solution to us.

### Requirements
- The app should have two screens: a list screen and a detail screen.
- The list screen should display a list of items retrieved from a webservice. Each item should display its title and a thumbnail image.
- Tapping on an item in the list should navigate to the detail screen, which should display the full details of the selected item.
- The app should fetch data from a webservice using KMM and the Kotlinx Serialization library.
- The app should use Jetpack Compose to display the UI.
- The app should handle errors gracefully and display appropriate error messages to the user.
- The app should be well-organized and modular, with clear separation of concerns.
- The app should be fully tested, with unit tests and integration tests for both the Android and KMM modules.

### Bonus Points
- Implement pagination or infinite scrolling on the list screen.
- Implement caching of data on the device to improve performance and reduce network usage.
- Use coroutines to manage asynchronous operations.
- Use dependency injection to manage dependencies.
- Add animations and transitions to create a more polished user experience.

### Submission
Please submit your completed code as a GitHub repository.
The repository should include a README.md file with instructions on how to build and run the app, as well as any other relevant information.


##Resources
Multiplatform Coroutines/ViewModel/ImageLoading solutions:
- Library to use Kotlin Coroutines from Swift code in KMP apps: https://github.com/rickclephas/KMP-NativeCoroutines
- Library to share Kotlin ViewModels with SwiftUI: https://github.com/rickclephas/KMM-ViewModel
- Compose Multiplatform Image Loader: https://github.com/qdsfdhvh/compose-imageloader

Logging:
- Kermit by Touchlab is a Kotlin Multiplatform centralized logging utility: https://kermit.touchlab.co/docs/

Dependency management:
- https://proandroiddev.com/better-dependencies-management-using-buildsrc-kotlin-dsl-eda31cdb81bf

Persistance:
- Create a multiplatform app using Ktor and SQLDelight – tutorial: https://kotlinlang.org/docs/multiplatform-mobile-ktor-sqldelight.html
- https://proandroiddev.com/kotlin-multiplatform-very-beginners-guide-part-3-database-e34c92daf41c

Unit Tests:
- Turbine (CashApp) - A small testing library for kotlinx.coroutines Flow: https://github.com/cashapp/turbine
- Kotlinx-coroutines-test - Utilities for efficiently testing coroutines: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/
- MocKMP: A mocking processor for Kotlin Multiplatform: https://github.com/kosi-libs/MocKMP
- https://medium.com/kodein-koders/mockmp-a-mocking-processor-for-kotlin-multiplatform-51957c484fe5

Inspiration:
- Star Wars API (No Images): https://swapi.dev/
- Star Wars API with images: https://github.com/akabab/starwars-api
- Joel O'Reilly blog on KMM/Compose: https://johnoreilly.dev/
- Joel O'Reilly blog on KMM ViewModel sharing: https://johnoreilly.dev/posts/kmm-viewmodel/
- Joel O'Reilly StarWars/KMM: https://github.com/joreilly/StarWars
- TouchLab KaMPKit: https://github.com/touchlab/KaMPKit
- Android Star Wars App: https://github.com/JoelKanyi/StarWars