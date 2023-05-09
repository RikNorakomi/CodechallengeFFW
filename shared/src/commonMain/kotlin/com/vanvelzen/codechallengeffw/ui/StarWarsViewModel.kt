package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.models.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class StarWarsViewModel(
    private val repository: StarWarsRepository,
    log: Logger
) : ViewModel() {

    var personName = ""
        private set

    init {
        log.e { "StarWarsViewModel instantiation!" }
    }


    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

//    @NativeCoroutinesState
//    val uiState: StateFlow<UiState> = flow {
//        emit(UiState.Success(repository.getPeople()))
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    init {
        fetchStarWarsCharacters()
    }

    private fun fetchStarWarsCharacters(){
        viewModelScope.launch {
            val people = repository.getPeople()
            val errorMessage: String? = null
            _uiState.update {
                UiState(
                    isLoading = false,
                    people = people.takeIf { it.isNotEmpty() },
                    error = errorMessage.takeIf { people.isEmpty() },
                    showEmptyState = people.isEmpty() && errorMessage == null
                )
            }
        }
    }

    fun onPullToRefresh() {
        TODO("Not yet implemented")
    }

    fun onPersonSelected(person: People) {
        personName = person.name
    }
}

data class UiState(
    val people: List<People>? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val showEmptyState: Boolean = false
)
