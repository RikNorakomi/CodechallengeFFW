package com.vanvelzen.codechallengeffw.ui

import co.touchlab.kermit.Logger
import com.vanvelzen.codechallengeffw.models.ViewModel

class TopBarViewModel(log: Logger) : ViewModel() {

    init {
        log.e { "TopBarViewModel instantiation!!" }
    }

    var title: String = ""
}