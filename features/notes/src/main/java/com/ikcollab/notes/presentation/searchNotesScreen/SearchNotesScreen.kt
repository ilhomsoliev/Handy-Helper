package com.ikcollab.notes.presentation.searchNotesScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ikcollab.components.CustomDialog
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesItem
import com.ikcollab.notes.presentation.components.searchNote
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchNotesScreen(
    state:SearchNotesState,
    onEvent: (SearchNotesEvent)->Unit,
) {
    val isDialogState = remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    CustomDialog(
        text = "Attention",
        description = "The entry will be permanently deleted",
        okBtnClick =
        {
            onEvent(SearchNotesEvent.DeleteNoteById)
            isDialogState.value = false
        },
        cancelBtnClick = { isDialogState.value = false },
        isDialogOpen = isDialogState.value,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        isDialogState.value = false
    }
    Scaffold() {  _ ->
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
                items(state.notes) { note ->
                    if (searchNote(note.title,note.description,state.search))
                     {
                        DraggableScaffold(
                            contentUnderRight = {
                                SwipeTrash(onTrashClick = {
                                    isDialogState.value = true
                                    onEvent(SearchNotesEvent.OnNoteIdChange(note.id!!))
                                })
                            },
                            contentUnderLeft = {
                                SwipeEdit(onClick = {
                                    Constants.WHICH_NOTE.value = Constants.EDIT_NOTE
                                    coroutineScope.launch {
                                        state.folders.forEach {
                                            if (it.id == note.folderId || note.folderId == -1) {
                                                onEvent(SearchNotesEvent.OnFolderIdChange(it.id!!))
                                                onEvent(SearchNotesEvent.OnNoteIdChange(note.id!!))
                                                Constants.NOTE_TITLE = note.title
                                                Constants.NOTE_DESCRIPTION = note.description
                                                Constants.NOTE_DATE_TIME = note.dateCreated
                                                Constants.FOLDER_NAME.value = if (note.folderId == -1) "FOLDER_NAME" else it.name
                                                delay(1)
                                                onEvent(SearchNotesEvent.NavigateToEditNote)
                                                return@launch
                                            }
                                        }
                                    }
                                })
                            },
                            contentOnTop = {
                                CustomNotesItem(
                                    title = note.title,
                                    description = note.description,
                                    dateTime = Date(note.dateCreated).toString(),
                                    onItemClick = {
                                        coroutineScope.launch {
                                            state.folders.forEach {
                                                if (it.id == note.folderId || note.folderId == -1) {
                                                    onEvent(SearchNotesEvent.OnFolderIdChange(it.id!!))
                                                    onEvent(SearchNotesEvent.OnNoteIdChange(note.id!!))
                                                    Constants.NOTE_TITLE = note.title
                                                    Constants.NOTE_DESCRIPTION = note.description
                                                    Constants.NOTE_DATE_TIME = note.dateCreated
                                                    Constants.FOLDER_NAME.value = if (note.folderId == -1) "FOLDER_NAME" else it.name
                                                    onEvent(SearchNotesEvent.NavigateToShowDetails)
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}