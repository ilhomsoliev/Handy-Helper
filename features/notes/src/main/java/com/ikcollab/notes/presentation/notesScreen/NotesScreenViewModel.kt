package com.ikcollab.notes.presentation.notesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.core.Constants
import com.ikcollab.domain.usecase.notes.folder.DeleteFolderByIdUseCase
import com.ikcollab.domain.usecase.notes.folder.GetFoldersUseCase
import com.ikcollab.domain.usecase.notes.note.DeleteAllNotesByFolderIdUseCase
import com.ikcollab.domain.usecase.notes.note.DeleteNoteByIdUseCase
import com.ikcollab.domain.usecase.notes.note.GetNotesByFolderIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase,
    private val deleteFolderByIdUseCase: DeleteFolderByIdUseCase,
    private val deleteAllNotesByFolderIdUseCase: DeleteAllNotesByFolderIdUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
    ):ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        NotesState()
    )

    init {
            onEvent(NotesEvent.GetFolders)
    }

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteAllNotesByFolderId -> {
                viewModelScope.launch {
                    deleteAllNotesByFolderIdUseCase(_state.value.folderId)
                }
            }
            is NotesEvent.GetFolders ->{
                viewModelScope.launch {
                    getFoldersUseCase().onEach { folders->
                        _state.update {
                            it.copy(folders = folders)
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is NotesEvent.GetNotesByFolderId ->{
                getNotesByFolderIdUseCase(folderId =-1).onEach { list ->
                    _state.update {
                        it.copy(notes = list)
                    }
                }.launchIn(viewModelScope)
                Constants.FOLDER_NAME.value = ""
                Constants.FOLDER_ID_IS_NULL.value = false
            }
            is NotesEvent.DeleteFolder->{
                viewModelScope.launch {
                    deleteFolderByIdUseCase(_state.value.folderId)
                }
            }
            is NotesEvent.OnFolderIdChange->{
                _state.update {
                    it.copy(folderId = event.value)
                }
            }
            is NotesEvent.DeleteNoteById -> {
                viewModelScope.launch {
                    deleteNoteByIdUseCase.invoke(_state.value.noteId)
                }
            }
            is NotesEvent.OnNoteIdChange->{
                _state.update {
                    it.copy(noteId = event.value)
                }
            }
            else ->{ }
        }
    }
}