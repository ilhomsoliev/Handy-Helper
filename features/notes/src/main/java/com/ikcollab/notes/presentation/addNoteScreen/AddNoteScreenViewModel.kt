package com.ikcollab.notes.presentation.addNoteScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.InsertNoteUseCase
import com.ikcollab.model.dto.note.NoteDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteScreenViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase
):ViewModel() {
    private val _stateNoteTitle = mutableStateOf("")
    val stateNoteTitle = _stateNoteTitle

    private val _stateNoteDescription = mutableStateOf("")
    val stateNoteDescription = _stateNoteDescription

    fun updateNoteTitle(title:String){
        _stateNoteTitle.value = title
    }

    fun updateNoteDescription(description:String){
        _stateNoteDescription.value = description
    }


    fun insertNoteToDatabase(
        folderId:Int,
        onDone:()->Unit,
    ) {
        viewModelScope.launch {
            insertNoteUseCase(
                NoteDto(
                    id = null,
                    title =  _stateNoteTitle.value,
                    description = _stateNoteDescription.value,
                    dateCreated = System.currentTimeMillis(),
                    folderId = folderId
                )
            )
            onDone()
        }
    }

}