package com.vanvelzen.codechallengeffw.android.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderErrorState
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderLoadingState
import com.vanvelzen.codechallengeffw.android.ui.shared.shimmerBrush
import com.vanvelzen.codechallengeffw.data.DummyDataSwapi
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import com.vanvelzen.codechallengeffw.ui.DetailScreenViewModel
import com.vanvelzen.codechallengeffw.ui.UiStateDetail.Error
import com.vanvelzen.codechallengeffw.ui.UiStateDetail.Loading
import com.vanvelzen.codechallengeffw.ui.UiStateDetail.Success
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    characterId: String?,
    log: Logger
) {
    val viewModel: DetailScreenViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    if (characterId.isNullOrEmpty()) {
        PlaceholderErrorState(error = "Unable to load character details. No character ID?!")
    } else {
        // TODO: Look into only one time fetching from api
        LaunchedEffect(true) {
            viewModel.fetchCharacterDetails(characterId)
        }
    }

    when (state){
        is Error -> PlaceholderErrorState(error = (state as Error).errorMessage)
        is Loading -> PlaceholderLoadingState(color = MaterialTheme.colors.onSurface)
        is Success -> {
            val character = (state as Success).character
            log.e { "Detail screen for Star Wars character with name:${character.name} url:${character.imageUrl}" }
            DetailScreenContent(character = character)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreenContent(character: StarWarsCharacter) {
    val showShimmer = remember { mutableStateOf(true) }
    val detailsMap = remember { character.createCharacterDetailsMap() }
    val keys = remember { detailsMap.keys.toList() }
    val values = remember { detailsMap.values.toList() }
    val numColumns = remember { 3 }
    val numRows = remember { (keys.size + numColumns - 1) / numColumns } // Round up to nearest integer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .clip(CircleShape)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
                .width(250.dp)
                .height(250.dp)
                .align(CenterHorizontally)
                .padding(top = 20.dp),
            contentDescription = character.name,
            contentScale = ContentScale.Inside,
            onSuccess = { showShimmer.value = false },
        )

        Card(
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(top = 20.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(size = 12.dp),
        ) {
            // Create
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {

                for (i in 0 until numRows) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        for (j in 0 until numColumns) {
                            val index = i * numColumns + j
                            if (index >= keys.size) {
                                // No more keys to display
                                break
                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .weight(1f)
                            ) {
                                ListItem(
                                    text = {
                                        Text(
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            text = keys[index].uppercase(),
                                            style = MaterialTheme.typography.subtitle2,
                                            color = Color.LightGray,
                                            maxLines = 1
                                        )
                                    },
                                    secondaryText = {
                                        Text(
                                            text = values[index],
                                            style = MaterialTheme.typography.subtitle1,
                                            color = Color.White,
                                            maxLines = 2,
                                        )
                                    }
                                )
                            }
                        }
                    }
                    if (i < numRows - 1) {
                        // Add divider after each row except the last one
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

fun StarWarsCharacter.createCharacterDetailsMap(): LinkedHashMap<String, String> {
    return with(this) {
        linkedMapOf(
            "name" to name,
            "birth year" to birthYear,
            "gender" to gender,
            "height" to height,
            "home world" to homeWorld,
            "mass" to mass,
            "skin color" to skinColor,
            "eye color" to eyeColor,
            "hair color" to hairColor,
        )
    }
}

@Preview
@Composable
fun DetailScreenContentPreview() {
    DetailScreenContent(
        character = DummyDataSwapi.items[0],
    )
}
