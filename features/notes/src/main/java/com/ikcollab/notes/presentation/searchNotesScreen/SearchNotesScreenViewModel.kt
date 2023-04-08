package com.ikcollab.notes.presentation.searchNotesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.folder.GetFoldersUseCase
import com.ikcollab.domain.usecase.notes.note.DeleteNoteByIdUseCase
import com.ikcollab.domain.usecase.notes.note.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNotesScreenViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val getFoldersUseCase: GetFoldersUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase
) : ViewModel() {

    init {
        onEvent(SearchNotesEvent.GetNotes)
        onEvent(SearchNotesEvent.GetFolders)
    }

    private val _state = MutableStateFlow(SearchNotesState())

    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        SearchNotesState()
    )

    fun onEvent(event: SearchNotesEvent){
        when(event){
            is SearchNotesEvent.OnFolderIdChange ->{
                _state.update {
                    it.copy(folderId = event.folderId)
                }
            }
            is SearchNotesEvent.OnNoteIdChange ->{
                _state.update {
                    it.copy(noteId = event.noteId)
                }
            }
            is SearchNotesEvent.OnDialogStateChange->{
                _state.update {
                    it.copy(isDialogState = event.state)
                }
            }
            is SearchNotesEvent.DeleteNoteById ->{
                viewModelScope.launch {
                    deleteNoteByIdUseCase.invoke(_state.value.noteId)
                }
            }
            is SearchNotesEvent.GetNotes ->{
                viewModelScope.launch {
                    getNotesUseCase().onEach { list ->
                        _state.update {
                            it.copy(list)
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is SearchNotesEvent.OnSearchChange ->{
                _state.update {
                    it.copy(search = event.search)
                }
            }
            is SearchNotesEvent.GetFolders ->{
                viewModelScope.launch {
                    getFoldersUseCase().onEach { folders->
                        _state.update {
                            it.copy(folders = folders)
                        }
                    }.launchIn(viewModelScope)
                }
            }
            else ->{}
        }
    }
}