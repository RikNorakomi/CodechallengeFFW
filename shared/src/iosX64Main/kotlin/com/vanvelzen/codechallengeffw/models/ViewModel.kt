package com.vanvelzen.codechallengeffw.models

import kotlinx.coroutines.CoroutineScope

actual abstract class ViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    protected actual open fun onCleared() {
    }
}