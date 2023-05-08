package com.vanvelzen.codechallengeffw.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel
import com.vanvelzen.codechallengeffw.android.R
import com.vanvelzen.codechallengeffw.data.DummyData
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.ui.OverviewViewModel

@Composable
fun OverViewScreen(
    navController: NavHostController
) {
    val viewModel = getViewModel<OverviewViewModel>()
    val state by viewModel.uiState.collectAsState()

    state.people?.let {
        if (it.isEmpty())
            Empty() else
            CharacterList(
                people = it,
                onItemClick = { item -> navController.navigate("${Routes.DetailScreen}/${item.url}") }
            )
    }
}

@Composable
fun Empty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.empty_state_text))
    }
}

@Composable
fun Error(error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)
    }
}

@Composable
fun CharacterList(people: List<People>, onItemClick: (People) -> Unit) {
    LazyColumn {
        items(people) { character ->
            StarWarsCharacterRow(character) {
                onItemClick(it)
            }
            Divider()
        }
    }
}

@Composable
fun StarWarsCharacterRow(character: People, onClick: (People) -> Unit) {
    Row(
        Modifier
            .clickable { onClick(character) }
            .padding(10.dp)
    ) {
        ThumbnailIcon(character)
        Text(character.name, Modifier.weight(1F))
    }
}

@Composable // TODO
fun ThumbnailIcon(people: People) {

}

@Preview
@Composable
fun MainScreenContentPreview_Success() {
    CharacterList(
        people = DummyData.items,
        onItemClick = {}
    )
}
