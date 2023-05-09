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
import com.vanvelzen.codechallengeffw.ui.UiStateDetail
import org.koin.androidx.compose.getViewModel
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
   state.character?.let {  DetailScreenContent(person = it) }
}

@Composable
fun DetailScreenContent(person: People) {
    val cardElementModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(horizontal = 5.dp)
        .background(Color.Blue)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(size = 12.dp),
        ) {
            Column(
//                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "Name:")
                        Text(text = person.name)
                    }
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "gender:")
                        Text(text = person.gender)
                    }
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "homeworld:")
                        Text(text = person.homeworld)
                    }
                }
                Divider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "Height:")
                        Text(text = person.height)
                    }
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "Mass:")
                        Text(text = person.mass)
                    }
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "Eye color:")
                        Text(text = person.eyeColor)
                    }
                }
                Divider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "Home world:")
                        Text(text = person.homeworld)
                    }
                    Column(cardElementModifier.weight(1f)) {
                        Text(text = "Skin color:")
                        Text(text = person.skinColor)
                    }
                    Column(cardElementModifier.weight(1f)){

                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun DetailScreenContentPreview() {
    DetailScreenContent(
        person = DummyData.items[0],
    )
}
