package com.ikcollab.notes.presentation.addNoteScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.InsertNoteUseCase
import com.ikcollab.model.dto.note.NoteDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNoteScreenViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase
):ViewModel() {
    private val _stateNoteTitle = mutableStateOf("")
    val stateNoteTitle = _stateNoteTitle

    @SuppressLint("NewApi")
    private val _stateNoteDate = mutableStateOf("${LocalDate.now().year}-${if(LocalDate.now().monthValue<=9) "0" else ""}${LocalDate.now().monthValue}-${if(LocalDate.now().dayOfMonth<=9) "0" else ""}${LocalDate.now().dayOfMonth}")
    val stateNoteDate = _stateNoteDate

    private val _stateNoteDescription = mutableStateOf("")
    val stateNoteDescription = _stateNoteDescription

    fun updateNoteDate(date:String){
        _stateNoteDate.value = date
    }

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
                    dateCreated = if(_stateNoteDate.value=="") System.currentTimeMillis() else SimpleDateFormat("yyyy-MM-dd").parse(_stateNoteDate.value).time,
                    folderId = folderId
                )
            )
            onDone()
        }
    }

}