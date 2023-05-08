package com.vanvelzen.codechallengeffw.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.vanvelzen.codechallengeffw.android.ui.OverViewScreen
import com.vanvelzen.codechallengeffw.android.ui.Routes
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.android.ui.DetailScreen
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

    // TODO: How to prevent multiple clicks on list item to recompose detail screen multiple times?
    // TODO: https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e

    NavHost(navController = navController, startDestination = Routes.OverviewScreen) {
        composable(Routes.OverviewScreen) { OverViewScreen(navController, sharedViewModel) }
        composable("${Routes.DetailScreen}/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            DetailScreen(itemId, sharedViewModel, log)
        }
    }
}
//
//@Composable
//fun StarWarsList(navController: NavHostController, viewModel: OverviewViewModel) {
////    val viewModel: OverviewViewModel = viewModel()
////    val items by remember { viewModel.uiState.value.people }
////    val isLoading by remember { viewModel.isLoading }
////    val errorMessage by remember { viewModel.errorMessage }
////    val context = LocalContext.current
////
////    LaunchedEffect(true) {
////        viewModel.getItems()
////    }
//
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val lifecycleAwareDogsFlow = remember(viewModel.uiState, lifecycleOwner) {
//        viewModel.uiState.flowWithLifecycle(lifecycleOwner.lifecycle)
//    }
//
//    @SuppressLint("StateFlowValueCalledInComposition") // False positive lint check when used inside collectAsState()
//    val uiState by lifecycleAwareDogsFlow.collectAsState(viewModel.uiState.value)
//
//
//
//    Surface(color = MaterialTheme.colors.background) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            if (uiState.isLoading) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//            } else if (uiState.error != null) {
//                Text(text = uiState.error!!, modifier = Modifier.align(Alignment.CenterHorizontally))
//            } else {
//                LazyColumn {
//                    items(uiState.people) { item ->
//                        StarWarsListItem(item, onClick = {
//                            navController.navigate("detail/${item.url}")
//                        })
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun StarWarsListItem(item: People, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//            .padding(16.dp)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = item.name,
//            modifier = Modifier.size(64.dp),
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Column {
//            Text(text = item.name, style = MaterialTheme.typography.h6)
//            Text(text = "Height: ${item.height} cm", style = MaterialTheme.typography.body1)
//            Text(text = "Mass: ${item.mass} kg", style = MaterialTheme.typography.body1)
//        }
//    }
//}
//
//@Composable
//fun StarWarsDetail(itemId: String?) {
//    Surface(color = MaterialTheme.colors.background) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            Text(text = "Detail screen for item $itemId")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    StarWarsApp(viewModel)
//}