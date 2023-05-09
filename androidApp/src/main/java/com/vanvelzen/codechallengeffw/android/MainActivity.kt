package com.vanvelzen.codechallengeffw.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.android.ui.detail.DetailScreen
import com.vanvelzen.codechallengeffw.android.ui.overview.OverViewScreen
import com.vanvelzen.codechallengeffw.android.ui.Routes
import com.vanvelzen.codechallengeffw.data.dto.getID
import com.vanvelzen.codechallengeffw.injectLogger
import com.vanvelzen.codechallengeffw.ui.TopBarViewModel
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
    val topBarViewModel = getViewModel<TopBarViewModel>()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isDetailScreenShowing = currentRoute?.contains(Routes.DetailScreen) == true
    val topBarTitle: String = if (isDetailScreenShowing) topBarViewModel.title else "Star Wars App"

    // TODO: How to prevent multiple clicks on list item to recompose detail screen multiple times?
    // TODO: https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            val textColor =
                if (isDetailScreenShowing) MaterialTheme.colors.onSurface else MaterialTheme.colors.onBackground
            if (isDetailScreenShowing) TopAppBar(
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = topBarTitle,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack, null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }) else
                Box(modifier = Modifier.fillMaxWidth().padding(all = 8.dp)) {
                    Text(
                        text = topBarTitle,
                        fontSize = 28.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            NavHost(navController = navController, startDestination = Routes.OverviewScreen) {
                composable(route = Routes.OverviewScreen) {
                    OverViewScreen() { person ->
                        topBarViewModel.title = person.name
                        navController.navigate("${Routes.DetailScreen}/${person.id}")
                    }
                }
                composable(route = "${Routes.DetailScreen}/{itemId}") { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getString("itemId")
                    DetailScreen(characterId, log)
                }
            }
        }
    }
}
