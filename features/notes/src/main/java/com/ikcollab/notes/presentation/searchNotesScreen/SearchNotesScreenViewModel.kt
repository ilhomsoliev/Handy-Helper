package com.ikcollab.notes.presentation.searchNotesScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.GetNotesUseCase
import com.ikcollab.notes.presentation.folderNotesScreen.FolderNotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNotesScreenViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    init {
        getNotes()
    }

    private val _state = MutableStateFlow(SearchNotesState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        SearchNotesState()
    )

    private var getNoteJob: Job? = null

    private fun getNotes() {
        getNoteJob?.cancel()
        viewModelScope.launch {
            getNoteJob = getNotesUseCase().onEach { list ->
                _state.update {
                    it.copy(list)
                }
            }.launchIn(viewModelScope)
        }
    }
}