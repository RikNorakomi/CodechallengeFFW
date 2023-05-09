package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.api.Response
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import com.vanvelzen.codechallengeffw.models.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class OverviewScreenViewModel(
    private val repository: StarWarsRepository,
    log: Logger
) : ViewModel() {



    init {
        log.e { "StarWarsViewModel instantiation!" }
    }


    private val _uiState: MutableStateFlow<UiStateOverview2> =
        MutableStateFlow(UiStateOverview2.Loading)
    val uiState: StateFlow<UiStateOverview2> = _uiState

//    @NativeCoroutinesState
//    val uiState: StateFlow<UiState> = flow {
//        emit(UiState.Success(repository.getPeople()))
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    init {
        fetchStarWarsCharacters()
    }

    private fun fetchStarWarsCharacters() {
        viewModelScope.launch {
            val response = repository.getAllCharacters()
            _uiState.update {
                when (response) {
                    is Response.Error -> UiStateOverview2.Error(response.errorMessage)
                    is Response.Success -> UiStateOverview2.Success(response.data)
                }
            }
        }
    }

    fun onPullToRefresh() {
        TODO("Not yet implemented")
    }

}

data class UiStateOverview(
    val people: List<StarWarsCharacter>? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val showEmptyState: Boolean = false
)


sealed class UiStateOverview2 {
    data class Success(val people: List<StarWarsCharacter>) : UiStateOverview2()
    data class Error(val errorMessage: String) : UiStateOverview2()
    object Loading : UiStateOverview2()
}
