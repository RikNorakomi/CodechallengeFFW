package com.vanvelzen.codechallengeffw.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.DummyData
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.ui.DetailScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    characterId: String?,
    log: Logger
) {
    if (characterId.isNullOrEmpty()) PlaceholderErrorState(error = "Unable to load character details. No character ID?!")

    val viewModel: DetailScreenViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchCharacterDetails(characterId)
    }

    // TODO Use SCSI for state to code gracefully handle various states

    log.v { "Detail screen for Star Wars character with name:${state.character?.name}" }
    state.character?.let { DetailScreenContent(person = it) }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreenContent(person: People) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val detailsMap = person.createCharacterDetailsMap()
                val keys = detailsMap.keys.toList()
                val values = detailsMap.values.toList()
                val numColumns = 3
                val numRows = (keys.size + numColumns - 1) / numColumns // Round up to nearest integer
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

private fun People.createCharacterDetailsMap(): HashMap<String, String> {
    return with(this) {
        linkedMapOf(
            "name" to name,
            "birth year" to birthYear,
            "gender" to gender,
            "height" to height,
            "home world" to homeworld,
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
        person = DummyData.items[0],
    )
}
