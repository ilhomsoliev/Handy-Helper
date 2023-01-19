package com.ikcollab.notes.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(

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
}