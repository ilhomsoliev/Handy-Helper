package com.ikcollab.notes.presentation.foldersNotesScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.DeleteNoteByIdUseCase
import com.ikcollab.domain.usecase.notes.note.GetNotesByFolderIdUseCase
import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.note.NoteState
import com.ikcollab.notes.presentation.addNoteScreen.AddNoteScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersNoteScreenViewModel @Inject constructor(
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase
) : ViewModel() {
    private val _stateNotesByFolderId = mutableStateOf(NoteState())
    val stateNotesByFolderId = _stateNotesByFolderId
    private val _state = MutableStateFlow(FoldersNotesScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        FoldersNotesScreenState()
    )

    fun onEvent(event: FoldersNotesScreenEvent){
        when(event){
            is FoldersNotesScreenEvent.GetNotesByFolderId->{
                getNotesByFolderIdUseCase(folderId = _state.value.folderId).onEach {
                    _state.value.note = NoteState(notes = it)
                }.launchIn(viewModelScope)
            }
            is FoldersNotesScreenEvent.DeleteNoteById ->{
                viewModelScope.launch {
                    Log.e("NOTE_IDD","${_state.value}")
                    deleteNoteByIdUseCase.invoke(_state.value.noteId)
                }
            }
            is FoldersNotesScreenEvent.OnFolderIdChange->{
                _state.update {
                    it.copy(folderId = event.folderId)
                }
            }
            is FoldersNotesScreenEvent.OnNoteIdChange->{
                _state.update {
                    it.copy(noteId = event.noteId)
                }
                Log.e("NOTE_ID","${_state.value}")
            }
            else ->{ }
        }
    }
//    fun getNotesByFolderId(folderId:Int,onFolderNameDismiss:()->Unit={}){
//        viewModelScope.launch {
//            if(folderId==-1)
//                onFolderNameDismiss()
//            getNotesByFolderIdUseCase(folderId = folderId).onEach {
//                _stateNotesByFolderId.value = _stateNotesByFolderId.value.copy(it)
//            }.launchIn(viewModelScope)
//        }
//    }
}