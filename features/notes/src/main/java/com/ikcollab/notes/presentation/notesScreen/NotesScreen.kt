@file:OptIn(ExperimentalMaterialApi::class)

package com.ikcollab.notes.presentation.notesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ikcollab.components.CustomDialog
import com.ikcollab.components.CustomFloatingActionButton
import com.ikcollab.components.CustomLoadingIndicator
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.components.CustomNotesItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    state: NotesState,
    onEvent: (NotesEvent)->Unit
) {

    val coroutineScope = rememberCoroutineScope()


    CustomDialog(
        text = "Attention",
        description = "Delete selected folder?",
        okBtnClick = {
            onEvent(NotesEvent.DeleteFolder)
            onEvent(NotesEvent.DeleteAllNotesByFolderId)
            state.isFolderDialogState = false
        },
        cancelBtnClick =
        {
            state.isFolderDialogState = false
        },
        isDialogOpen = state.isFolderDialogState,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        state.isFolderDialogState = false
    }
    CustomDialog(
        text = "Attention",
        description = "The entry will be permanently deleted",
        okBtnClick =
        {
            onEvent(NotesEvent.DeleteNoteById)
            state.isNoteDialogState = false
        },
        cancelBtnClick = { state.isNoteDialogState = false },
        isDialogOpen = state.isNoteDialogState,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        state.isNoteDialogState = false
    }
    val isLoading = remember {
        mutableStateOf(false)
    }
    CustomLoadingIndicator(isLoading = isLoading.value)
    coroutineScope.launch {
        delay(100)
        isLoading.value = false
    }
    Scaffold(floatingActionButton = {
        CustomFloatingActionButton(
            onInsert = {
                Constants.WHICH_FOLDER.value = Constants.ADD_FOLDER
                onEvent(NotesEvent.OpenBottomSheetToAdd)
            },
            onEdit = {
                Constants.WHICH_NOTE.value = Constants.ADD_NOTE
                Constants.FOLDER_ID_IS_NULL.value = true
                onEvent(NotesEvent.NavigateToAddNote)
            },
            isMultiple = true
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                items(state.folders) { folder ->
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeTrash(onTrashClick = {
                                onEvent(NotesEvent.OnFolderIdChange(folder.id!!))
                                state.isFolderDialogState = true
                            })
                        },
                        contentUnderLeft = {
                            SwipeEdit(onClick = {
                                coroutineScope.launch {
                                    onEvent(NotesEvent.OnFolderIdChange(folder.id!!))
                                    Constants.FOLDER_NAME.value = folder.name
                                    delay(1)
                                    Constants.WHICH_FOLDER.value = Constants.EDIT_FOLDER
                                    onEvent(NotesEvent.NavigateToEditFolder)
                                }
                            })
                        },
                        contentOnTop = {
                            CustomNotesCategory(
                                onClick = {
                                    coroutineScope.launch {
                                        onEvent(NotesEvent.OnFolderIdChange(folder.id!!))
                                        delay(1)
                                        onEvent(NotesEvent.NavigateToFoldersDetails)
                                        Constants.FOLDER_NAME.value = folder.name
                                        Constants.MAIN_FOLDER_NAME.value = folder.name
                                    }
                                },
                                icon = Icons.Default.Folder,
                                title = folder.name,
                                number = folder.countOfNotes
                            )
                        },
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(25.dp))
                }
                items(state.notes.filter { it.folderId == -1 }) { note ->
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeTrash(onTrashClick = {
                                onEvent(NotesEvent.OnNoteIdChange(note.id!!))
                                state.isNoteDialogState = true
                            })
                        },
                        contentUnderLeft = {
                            SwipeEdit(onClick = {
                                coroutineScope.launch {
                                    onEvent(NotesEvent.OnNoteIdChange(note.id!!))
                                    delay(1)
                                    Constants.WHICH_NOTE.value = Constants.EDIT_NOTE
                                    onEvent(NotesEvent.NavigateToEditNote)
                                }
                            })
                        },
                        contentOnTop = {
                            CustomNotesItem(
                                title = note.title,
                                description = note.description,
                                dateTime = Date(note.dateCreated).toString(),
                                onItemClick = {
                                    onEvent(NotesEvent.OnNoteIdChange(note.id!!))
                                    onEvent(NotesEvent.NavigateToShowNotesDetails)
                                    Constants.NOTE_TITLE = note.title
                                    Constants.NOTE_DESCRIPTION = note.description
                                    Constants.NOTE_DATE_TIME = note.dateCreated
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}