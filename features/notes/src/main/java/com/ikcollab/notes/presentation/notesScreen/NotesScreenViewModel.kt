package com.ikcollab.notes.presentation.notesScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.folder.DeleteFolderByIdUseCase
import com.ikcollab.domain.usecase.notes.folder.GetFoldersUseCase
import com.ikcollab.domain.usecase.notes.note.CountNotesOfFolderUseCase
import com.ikcollab.domain.usecase.notes.note.DeleteAllNotesByFolderIdUseCase
import com.ikcollab.domain.usecase.notes.note.GetNotesByFolderIdUseCase
import com.ikcollab.domain.usecase.notes.note.InsertNoteUseCase
import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.model.dto.note.FolderState
import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.note.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase,
    private val deleteFolderByIdUseCase: DeleteFolderByIdUseCase,
    private val countNotesOfFolderUseCase: CountNotesOfFolderUseCase,
    private val deleteAllNotesByFolderIdUseCase: DeleteAllNotesByFolderIdUseCase
):ViewModel() {

    private val _stateFolder = mutableStateOf(FolderState())
    val stateFolder = _stateFolder

    private val _stateNotesFolderId = mutableStateOf(0)
    val stateNotesFolderId = _stateNotesFolderId

    private val _stateNotesFolderName = mutableStateOf("")
    val stateNotesFolderName = _stateNotesFolderName


    private var getFolderJob: Job? = null

    private var getNoteJob: Job? = null

    init {
        getFolders()
    }


    fun updateNotesFolderIdAndName(id:Int,name:String){
        _stateNotesFolderId.value = id
        _stateNotesFolderName.value = name
    }

    fun deleteFolder(
        id:Int,
        name:String,
        dateCreated: Long
    ) {
        viewModelScope.launch {
            deleteFolderByIdUseCase(FolderDto(id, name, dateCreated))
        }
    }

    fun deleteAllNotesByFolderId(
        folderId:Int
    ){
     viewModelScope.launch {
         deleteAllNotesByFolderIdUseCase(folderId)
     }
    }


    fun getFolders(){
        getFolderJob?.cancel()
        viewModelScope.launch {
            getFolderJob = getFoldersUseCase().onEach {
                _stateFolder.value = _stateFolder.value.copy(it)
            }.launchIn(viewModelScope)
        }
    }
}