package com.ikcollab.notes.presentation.bottomSheetInsertFolder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.notes.folder.InsertFolderUseCase
import com.ikcollab.model.dto.note.FolderDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetInsertFolderViewModel@Inject constructor(
    private val insertFolderUseCase: InsertFolderUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BottomSheetInsertFolderState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetInsertFolderState()
    )
    private val channel = Channel<BottomSheetInsertFolderOneTimeEvent>()
    val flow = channel.receiveAsFlow()
    fun onEvent(event: BottomSheetInsertFolderEvent){
        when(event){
            is BottomSheetInsertFolderEvent.OnFolderNameChange -> {
                _state.update {
                    it.copy(
                        folderName = event.value
                    )
                }
            }
            is BottomSheetInsertFolderEvent.InsertFolder -> {
                val name = _state.value.folderName
                val dateCreated = System.currentTimeMillis()
                if (name.isEmpty()) return
                viewModelScope.launch {
                    Log.e("insert","${_state.value.folderId}")
                    insertFolderUseCase(
                        FolderDto(
                            name = name,
                            dateCreated = dateCreated,
                            id = if(_state.value.folderId!=-1) _state.value.folderId else null
                        )
                    )
                    channel.send(BottomSheetInsertFolderOneTimeEvent.CloseBottomSheet)
                }
                onEvent(BottomSheetInsertFolderEvent.OnFolderNameChange(""))
            }
            is BottomSheetInsertFolderEvent.OnFolderIdChange->{
                _state.update {
                    it.copy(folderId = event.value)
                }
                Log.e("insert___","${event.value}//${_state.value.folderId}")
            }
        }
    }
}