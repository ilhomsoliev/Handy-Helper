package com.ikcollab.notes.presentation.addNoteScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.note.GetNoteByIdUseCase
import com.ikcollab.domain.usecase.notes.note.InsertNoteUseCase
import com.ikcollab.domain.usecase.notes.note.UpdateNoteUseCase
import com.ikcollab.model.dto.note.NoteDto
import com.ikcollab.model.dto.toNoteDto
import com.ikcollab.model.local.note.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNoteScreenViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
) : ViewModel() {
    private val _stateNoteTitle = mutableStateOf("")
    val stateNoteTitle = _stateNoteTitle

    @SuppressLint("NewApi")
    private val _stateNoteDate =
        mutableStateOf("${LocalDate.now().year}-${if (LocalDate.now().monthValue <= 9) "0" else ""}${LocalDate.now().monthValue}-${if (LocalDate.now().dayOfMonth <= 9) "0" else ""}${LocalDate.now().dayOfMonth}")
    val stateNoteDate = _stateNoteDate

    private val _stateNoteDescription = mutableStateOf("")
    val stateNoteDescription = _stateNoteDescription


    private val _state = MutableStateFlow(AddNoteScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AddNoteScreenState()
    )

    fun onEvent(event: AddNoteScreenEvent) {
        when (event) {
            is AddNoteScreenEvent.OnLoadNote -> {
                if (event.noteId == -1) {
                    _state.update {
                        it.copy(
                            note = NoteEntity(
                                title = "",
                                description = "",
                                dateCreated = System.currentTimeMillis(),
                                folderId = -1
                            )
                        )
                    }
                } else {
                    viewModelScope.launch {
                        val note: NoteEntity? = getNoteByIdUseCase.invoke(event.noteId)
                        note?.let { newNote ->
                            _state.update {
                                it.copy(
                                    note = newNote
                                )
                            }
                        }
                    }
                }
            }
            is AddNoteScreenEvent.OnTitleChange -> {
                _state.update {
                    it.copy(note = _state.value.note?.copy(title = event.value))
                }
            }
            is AddNoteScreenEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(note = _state.value.note?.copy(description = event.value))
                }
            }
            is AddNoteScreenEvent.InsertToDatabase -> {
                viewModelScope.launch {
                    _state.value.note?.toNoteDto()?.let { insertNoteUseCase.invoke(it) }
                }
            }

            else -> {}
        }
    }

    @SuppressLint("NewApi")
    fun clear() {
        updateNoteDate("${LocalDate.now().year}-${if (LocalDate.now().monthValue <= 9) "0" else ""}${LocalDate.now().monthValue}-${if (LocalDate.now().dayOfMonth <= 9) "0" else ""}${LocalDate.now().dayOfMonth}")
        updateNoteDescription("")
        updateNoteTitle("")
    }

    fun updateNoteDate(date: String) {
        _stateNoteDate.value = date
    }

    fun updateNoteTitle(title: String) {
        _stateNoteTitle.value = title
    }

    fun updateNoteDescription(description: String) {
        _stateNoteDescription.value = description
    }


    fun insertNoteToDatabase(
        folderId: Int,
        onDone: () -> Unit,
    ) {
        viewModelScope.launch {
            insertNoteUseCase(
                NoteDto(
                    id = null,
                    title = _stateNoteTitle.value,
                    description = _stateNoteDescription.value,
                    dateCreated = if (_stateNoteDate.value == "") System.currentTimeMillis() else SimpleDateFormat(
                        "yyyy-MM-dd"
                    ).parse(_stateNoteDate.value).time,
                    folderId = folderId
                )
            )
            onDone()
        }
    }

    fun updateNoteInDatabase(
        folderId: Int,
        noteId: Int,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            insertNoteUseCase(
                NoteDto(
                    id = noteId,
                    title = _stateNoteTitle.value,
                    description = _stateNoteDescription.value,
                    dateCreated = if (_stateNoteDate.value == "") System.currentTimeMillis() else SimpleDateFormat(
                        "yyyy-MM-dd"
                    ).parse(_stateNoteDate.value).time,
                    folderId = folderId
                )
            )
            onDone()
        }
    }
}