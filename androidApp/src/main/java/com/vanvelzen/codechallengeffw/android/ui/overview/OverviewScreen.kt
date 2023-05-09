package com.vanvelzen.codechallengeffw.android.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanvelzen.codechallengeffw.android.ui.CustomDivider
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderEmptyState
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderErrorState
import com.vanvelzen.codechallengeffw.android.ui.shared.PlaceholderLoadingState
import com.vanvelzen.codechallengeffw.data.DummyDataSwapi
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.dto.PeopleWithImages
import com.vanvelzen.codechallengeffw.ui.OverviewScreenViewModel
import com.vanvelzen.codechallengeffw.ui.UiStateOverview2
import org.koin.androidx.compose.koinViewModel

@Composable
fun OverViewScreen(
    onItemClick: (People) -> Unit
) {
    val viewModel: OverviewScreenViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    when (state){
        is UiStateOverview2.Error -> PlaceholderErrorState(error = (state as UiStateOverview2.Error).errorMessage)
        is UiStateOverview2.Loading -> PlaceholderLoadingState()
        is UiStateOverview2.Success -> {
            val characters = (state as UiStateOverview2.Success).people
            if (characters.isEmpty()) PlaceholderEmptyState()
            else CharacterList(
                people = characters,
                onItemClick = { people -> onItemClick(people) }
            )
        }
    }
//    // TODO: Add more graceful impl with sealed classes
//    if (state.error != null) PlaceholderErrorState(error = state.error!!)
//    else if (state.isLoading) PlaceholderLoadingState()
//    else state.people?.let {
//        if (it.isEmpty()) PlaceholderEmptyState() else CharacterList(people = it,
//            onItemClick = { people -> onItemClick(people) })
//    }
}

@Composable
fun CharacterList(people: List<PeopleWithImages>, onItemClick: (PeopleWithImages) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(all = 8.dp)
            .padding(top = 20.dp)
    ) {
        items(people) { character ->
            StarWarsCharacterRow(character) {
                onItemClick(it)
            }
            CustomDivider()
        }
    }
}

@Composable
fun StarWarsCharacterRow(character: PeopleWithImages, onClick: (PeopleWithImages) -> Unit) {
    Row(
        Modifier
            .clickable { onClick(character) }
            .padding(10.dp)) {
        ThumbnailIcon(character)
        Text(
            text = character.name,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.weight(1F)
        )
    }
}

@Composable // TODO
fun ThumbnailIcon(people: PeopleWithImages) {

}

@Preview
@Composable
fun MainScreenContentPreview_Success() {
    CharacterList(people = DummyDataSwapi.items, onItemClick = {})
}
