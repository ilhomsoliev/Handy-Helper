package com.ikcollab.notes.presentation.folderNotesScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.DeleteNoteByIdUseCase
import com.ikcollab.domain.usecase.notes.note.GetNotesByFolderIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderNotesViewModel @Inject constructor(
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase
) : ViewModel() {
    private val _stateNotesByFolderId = mutableStateOf(FolderNotesState())
    val stateNotesByFolderId = _stateNotesByFolderId
    private val _state = MutableStateFlow(FolderNotesState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        FolderNotesState()
    )

    fun onEvent(event: FolderNotesEvent) {
        when (event) {
            is FolderNotesEvent.GetNotesByFolderId -> {
                getNotesByFolderIdUseCase(folderId = _state.value.folderId).onEach { list ->
                    _state.update {
                        it.copy(notes = list)
                    }
                }.launchIn(viewModelScope)
            }
            is FolderNotesEvent.DeleteNoteById -> {
                viewModelScope.launch {
                    deleteNoteByIdUseCase.invoke(_state.value.noteId)
                }
            }
            is FolderNotesEvent.OnFolderIdChange -> {
                _state.update {
                    it.copy(folderId = event.folderId)
                }
            }
            is FolderNotesEvent.OnNoteIdChange -> {
                _state.update {
                    it.copy(noteId = event.noteId)
                }
            }

            else -> {}
        }
    }
}