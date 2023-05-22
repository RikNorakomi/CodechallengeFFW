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
        log.v { "DetailScreenViewModel instantiation!" }
    }

    private val _uiState: MutableStateFlow<UiStateDetail>
    = MutableStateFlow(UiStateDetail.Loading)
    val uiState: StateFlow<UiStateDetail> = _uiState

    fun fetchCharacterDetails(characterId: String) {
        viewModelScope.launch {
            if (characterId.isEmpty()){
                _uiState.update {
                    UiStateDetail.Error("Unable to load character details")
                }
                return@launch
            }

            val response = repository.getCharacterDetails(characterId)
            _uiState.update {
                when (response) {
                    is Response.Error -> UiStateDetail.Error(response.errorMessage)
                    is Response.Success -> UiStateDetail.Success(response.data)
                }
            }
        }
    }
}

sealed class UiStateDetail {
    data class Success(val character: StarWarsCharacter) : UiStateDetail()
    data class Error(val errorMessage: String) : UiStateDetail()
    object Loading : UiStateDetail()
}

