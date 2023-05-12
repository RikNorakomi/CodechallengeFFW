package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.data.remote.Response
import com.vanvelzen.codechallengeffw.data.repository.StarWarsRepository
import com.vanvelzen.codechallengeffw.models.StarWarsCharacter
import com.vanvelzen.codechallengeffw.models.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class DetailScreenViewModel(
    private val repository: StarWarsRepository,
    log: Logger,
) : ViewModel() {

    init {
        log.e { "DetailScreenViewModel instantiation!" }
    }

    private val _uiState: MutableStateFlow<UiStateDetail2>
    = MutableStateFlow(UiStateDetail2.Loading)
    val uiState: StateFlow<UiStateDetail2> = _uiState

    fun fetchCharacterDetails(characterId: String?) {
        viewModelScope.launch {
            if (characterId.isNullOrEmpty()){
                _uiState.update {
                    UiStateDetail2.Error("Unable to load character details")
                }
                return@launch
            }

            val response = repository.getCharacterDetails(characterId)
            val errorMessage: String? = null
            _uiState.update {
                when (response) {
                    is Response.Error -> UiStateDetail2.Error(response.errorMessage)
                    is Response.Success -> UiStateDetail2.Success(response.data)
                }
//                UiStateDetail(
//                    character = people,
//                    isLoading = false,
//                    error = errorMessage.takeIf { people == null },
//                )
            }
        }
    }
}

data class UiStateDetail(
    val character: StarWarsCharacter? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class UiStateDetail2 {
    data class Success(val character: StarWarsCharacter) : UiStateDetail2()
    data class Error(val errorMessage: String) : UiStateDetail2()
    object Loading : UiStateDetail2()
}

