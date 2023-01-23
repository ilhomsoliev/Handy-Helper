package com.ikcollab.notes.presentation.searchNotesScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.GetNotesUseCase
import com.ikcollab.model.dto.note.FolderState
import com.ikcollab.model.dto.note.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNotesScreenViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
):ViewModel() {

    init {
        getNotes()
    }
    private val _stateSearchNotes = mutableStateOf("")
    val stateSearchNotes = _stateSearchNotes

    private val _stateNotes = mutableStateOf(NoteState())
    val stateNotes = _stateNotes

    private var getNoteJob: Job? = null

    fun changeSearchNotes(search:String){
        _stateSearchNotes.value = search
    }
    fun getNotes(){
        getNoteJob?.cancel()
        viewModelScope.launch {
            getNoteJob = getNotesUseCase().onEach {
                _stateNotes.value = _stateNotes.value.copy(it)
            }.launchIn(viewModelScope)
        }
    }
}