package com.vanvelzen.codechallengeffw.ui

import com.vanvelzen.codechallengeffw.data.dto.People
import com.vanvelzen.codechallengeffw.models.ViewModel

class TopBarViewModel : ViewModel() {

    var selectedPerson: People? = null
        private set


    fun onPersonSelected(person: People) {
        selectedPerson = person
    }

}