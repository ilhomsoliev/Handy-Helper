package com.ikcollab.components.DraggableCard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CardsScreenViewModel : ViewModel() {

    private val _revealedCardIdsList = mutableStateOf(listOf<Int>())
    val revealedCardIdsList: MutableState<List<Int>> get() = _revealedCardIdsList

    fun onItemExpanded(cardId: Int) {
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    fun onItemCollapsed(cardId: Int) {
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }
}
