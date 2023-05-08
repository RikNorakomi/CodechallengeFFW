package com.vanvelzen.codechallengeffw.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.ui.StarWarsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    characterId: String?,
    viewModel: StarWarsViewModel,
    log: Logger
) {

    val state by viewModel.uiState.collectAsState()

    log.v { "Detail screen for Star Wars character with id:$characterId" }


}
