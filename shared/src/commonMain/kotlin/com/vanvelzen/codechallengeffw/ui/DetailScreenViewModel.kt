package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.models.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class DetailScreenViewModel(
    private val repository: StarWarsRepository,
    log: Logger
) : ViewModel() {

    init {
        log.e { "DetailScreenViewModel instantiation!" }
    }

    private val _uiState: MutableStateFlow<UiStateDetail> = MutableStateFlow(UiStateDetail(isLoading = true))
    val uiState: StateFlow<UiStateDetail> = _uiState

    fun fetchCharacterDetails(characterId: String?) {
        viewModelScope.launch {
            if (characterId.isNullOrEmpty()){
                _uiState.update {
                    UiStateDetail(
                        isLoading = false,
                        error = "Unable to load character details"
                    )
                }
                return@launch
            }

            val people = repository.getCharacterDetails(characterId)
            val errorMessage: String? = null
            _uiState.update {
                UiStateDetail(
                    character = people,
                    isLoading = false,
                    error = errorMessage.takeIf { people == null },
                )
            }
        }
    }
}

data class UiStateDetail(
    val character: People? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)