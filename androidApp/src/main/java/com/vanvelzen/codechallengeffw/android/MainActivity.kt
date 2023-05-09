package com.vanvelzen.codechallengeffw.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.android.ui.DetailScreen
import com.vanvelzen.codechallengeffw.android.ui.OverViewScreen
import com.vanvelzen.codechallengeffw.android.ui.Routes
import com.vanvelzen.codechallengeffw.data.dto.getID
import com.vanvelzen.codechallengeffw.injectLogger
import com.vanvelzen.codechallengeffw.ui.StarWarsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Logger by injectLogger("MainActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                StarWarsApp(log)
            }
        }
    }
}

@Composable
fun StarWarsApp(log: Logger) {

    val navController = rememberNavController()
    val starWarsViewModel = getViewModel<StarWarsViewModel>()
    val sharedViewModel: StarWarsViewModel = remember { starWarsViewModel } // prevents reinitialization of ViewModel that's shared across composables
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val topBarTitle = if (currentRoute?.contains(Routes.DetailScreen) == true) sharedViewModel.personName else "Star Wars App"

    // TODO: How to prevent multiple clicks on list item to recompose detail screen multiple times?
    // TODO: https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e

    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(topBarTitle)
                },
                navigationIcon = {
                    if (currentRoute != Routes.OverviewScreen) IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                })

            NavHost(navController = navController, startDestination = Routes.OverviewScreen) {
                composable(Routes.OverviewScreen) {
                    OverViewScreen(sharedViewModel) { person ->
                        sharedViewModel.onPersonSelected(person)
                        navController.navigate("${Routes.DetailScreen}/${person.getID()}") }
                }
                composable("${Routes.DetailScreen}/{itemId}") { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString("itemId")
                    DetailScreen(itemId, sharedViewModel, log)
                }
            }
        }
    }
}
