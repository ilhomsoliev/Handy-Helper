package com.ikcollab.notes.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.folder.InsertFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val insertFolderUseCase: InsertFolderUseCase
):ViewModel() {
    private val _stateSearchNotes = mutableStateOf("")
    val stateSearchNotes = _stateSearchNotes
    private val _stateNumberCategoriesNote = mutableStateOf(0)
    val stateNumberCategoriesNote = _stateNumberCategoriesNote

    fun changeSearchNotes(search:String){
        _stateSearchNotes.value = search
    }

    fun updateNumberCategoriesNote(number:Int){
        _stateNumberCategoriesNote.value = number
    }
//    fun insertFolder(
//        id:Int,
//        name:String,
//        dateCreated:Long
//    ){
//        viewModelScope.launch {
//            insertFolderUseCase(FolderDto(id,name,dateCreated))
//        }
//    }
}