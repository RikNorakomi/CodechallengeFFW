package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.Response
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import com.vanvelzen.codechallengeffw.models.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class OverviewScreenViewModel(
    private val repository: StarWarsRepository,
    private val log: Logger
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiStateOverview> =
        MutableStateFlow(UiStateOverview.Loading)
    val uiState: StateFlow<UiStateOverview> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        log.e { "StarWarsViewModel instantiation!" }
    }

    init {
        fetchStarWarsCharacters()
    }

    private fun fetchStarWarsCharacters() {
        viewModelScope.launch {
            val response = repository.getStarWarsCharacters()
            _uiState.update {
                when (response) {
                    is Response.Error -> UiStateOverview.Error(response.errorMessage)
                    is Response.Success -> UiStateOverview.Success(response.data)
                }
            }
        }
    }

    fun onPullToRefresh() {
        log.v { "Pull to refresh triggered" }
        fetchStarWarsCharacters()
    }
}

sealed class UiStateOverview {
    data class Success(val people: List<StarWarsCharacter>) : UiStateOverview()
    data class Error(val errorMessage: String) : UiStateOverview()
    object Loading : UiStateOverview()
}
