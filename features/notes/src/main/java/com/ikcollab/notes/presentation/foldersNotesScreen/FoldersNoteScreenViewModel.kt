package com.ikcollab.notes.presentation.foldersNotesScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.GetNotesByFolderIdUseCase
import com.ikcollab.model.dto.note.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersNoteScreenViewModel @Inject constructor(
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
) : ViewModel() {
    private val _stateNotesByFolderId = mutableStateOf(NoteState())
    val stateNotesByFolderId = _stateNotesByFolderId

    fun getNotesByFolderId(folderId:Int){
        viewModelScope.launch {
            getNotesByFolderIdUseCase(folderId = folderId).onEach {
                _stateNotesByFolderId.value = _stateNotesByFolderId.value.copy(it)
            }.launchIn(viewModelScope)
        }
    }
}