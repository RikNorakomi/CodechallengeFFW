package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.DummyData
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.models.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

open class OverviewViewModel(
    private val repository: StarWarsRepository,
    log: Logger
) : ViewModel() {

    private val log = log.withTag("OverviewViewModel")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

//    @NativeCoroutinesState
//    val uiState: StateFlow<UiState> = flow {
//        emit(UiState.Success(repository.getPeople()))
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    init {
        _uiState.update {
            // TODO: Update to repo function invocation
            val error = null
            val errorMessage = if (error != null) {
                "Unable to download breed list"
            } else null

            val people = DummyData.items + DummyData.items
            UiState(
                isLoading = false,
                people = people.takeIf { it.isNotEmpty() },
                error = errorMessage.takeIf { people.isEmpty() },
                showEmptyState = people.isEmpty() && errorMessage == null
            )
        }
    }


    fun onPullToRefresh() {
        TODO("Not yet implemented")
    }
}

data class UiState(
    val people: List<People>? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val showEmptyState: Boolean = false
)
