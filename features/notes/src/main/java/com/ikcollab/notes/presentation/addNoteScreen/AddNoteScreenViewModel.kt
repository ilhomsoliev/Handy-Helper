package com.ikcollab.notes.presentation.addNoteScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.folder.GetFoldersUseCase
import com.ikcollab.domain.usecase.notes.note.GetNoteByIdUseCase
import com.ikcollab.domain.usecase.notes.note.InsertNoteUseCase
import com.ikcollab.model.dto.toNoteDto
import com.ikcollab.model.local.note.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class AddNoteScreenViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val getFoldersUseCase: GetFoldersUseCase,
    ) : ViewModel() {
    private val _state = MutableStateFlow(AddNoteScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AddNoteScreenState()
    )

    private var getFolderJob: Job? = null

    @SuppressLint("SimpleDateFormat")
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
                                folderId = event.folderId
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
            is AddNoteScreenEvent.OnDateChange ->{
                _state.update {
                    it.copy(note = _state.value.note?.copy(dateCreated = if (event.value == "") System.currentTimeMillis() else SimpleDateFormat("yyyy-MM-dd").parse(event.value).time))
                }
            }
            is AddNoteScreenEvent.OnFolderChange ->{
                _state.update {
                    it.copy(note=_state.value.note?.copy(folderId = event.value))
                }
                Log.e("FOLDERCHANGE","${event.value}")
            }
            is AddNoteScreenEvent.GetFolders ->{
                getFolderJob?.cancel()
                viewModelScope.launch {
                    getFolderJob = getFoldersUseCase().onEach { folder->
                        _state.update {
                            it.copy(folder = folder)
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is AddNoteScreenEvent.InsertToDatabase -> {
                viewModelScope.launch {
                    _state.value.note?.toNoteDto()?.let {
                        insertNoteUseCase.invoke(it)
                    }
                }
            }
            else -> {}
        }
    }
}