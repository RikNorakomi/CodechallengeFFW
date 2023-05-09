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
import com.vanvelzen.codechallengeffw.data.DummyData
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.ui.OverviewScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OverViewScreen(
    onItemClick: (People) -> Unit
) {
    val viewModel: OverviewScreenViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    // TODO: Add more graceful impl with sealed classes
    if (state.error != null) PlaceholderErrorState(error = state.error!!)
    else if (state.isLoading) PlaceholderLoadingState()
    else state.people?.let {
        if (it.isEmpty()) PlaceholderEmptyState() else CharacterList(people = it,
            onItemClick = { people -> onItemClick(people) })
    }
}

@Composable
fun CharacterList(people: List<People>, onItemClick: (People) -> Unit) {
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
fun StarWarsCharacterRow(character: People, onClick: (People) -> Unit) {
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
fun ThumbnailIcon(people: People) {

}

@Preview
@Composable
fun MainScreenContentPreview_Success() {
    CharacterList(people = DummyData.items, onItemClick = {})
}
