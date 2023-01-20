package com.ikcollab.notes.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.folder.GetFoldersUseCase
import com.ikcollab.domain.usecase.notes.folder.InsertFolderUseCase
import com.ikcollab.model.dao.note.FolderDto
import com.ikcollab.model.dao.note.FolderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val insertFolderUseCase: InsertFolderUseCase,
    private val getFoldersUseCase: GetFoldersUseCase
):ViewModel() {
    private val _stateFolderName = mutableStateOf("")
    val stateFolderName = _stateFolderName

    private val _stateSearchNotes = mutableStateOf("")
    val stateSearchNotes = _stateSearchNotes

    private val _stateNumberCategoriesNote = mutableStateOf(0)
    val stateNumberCategoriesNote = _stateNumberCategoriesNote

    private val _stateFolder = mutableStateOf(FolderState())
    val stateFolder = _stateFolder

    private var getFolderJob: Job? = null

    init {
        getFolders()
    }

    fun changeSearchNotes(search:String){
        _stateSearchNotes.value = search
    }

    fun setFolderName(folder:String){
        _stateFolderName.value = folder
    }
    fun updateNumberCategoriesNote(number:Int){
        _stateNumberCategoriesNote.value = number
    }

    fun insertFolder(
        id:Int,
        name:String,
        dateCreated:Long = System.currentTimeMillis()
    ){
        viewModelScope.launch {
            insertFolderUseCase(FolderDto(id,name,dateCreated))
        }
        _stateFolderName.value = ""
    }
    private fun getFolders(){
        getFolderJob?.cancel()
        viewModelScope.launch {
            getFolderJob = getFoldersUseCase().onEach {
                _stateFolder.value = _stateFolder.value.copy(it)
            }.launchIn(viewModelScope)
        }
    }
}