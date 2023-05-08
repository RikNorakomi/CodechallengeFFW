package com.vanvelzen.codechallengeffw.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vanvelzen.codechallengeffw.ui.OverviewViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(
    characterId: String?
) {

    val viewModel = getViewModel<OverviewViewModel>()
    val state by viewModel.uiState.collectAsState()



}
