package com.ikcollab.notes.presentation.folderNotesScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ikcollab.components.CustomDialog
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FoldersNoteScreen(
    state: FolderNotesState,
    onEvent: (FolderNotesEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    CustomDialog(
        text = "Attention",
        description = "The entry will be permanently deleted",
        okBtnClick =
        {
            onEvent(FolderNotesEvent.DeleteNoteById)
            onEvent(FolderNotesEvent.OnDialogStateChange(false))
        },
        cancelBtnClick = { onEvent(FolderNotesEvent.OnDialogStateChange(false))
        },
        isDialogOpen = state.isDialogState,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        onEvent(FolderNotesEvent.OnDialogStateChange(false))
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(backgroundColor = MaterialTheme.colors.secondary, onClick = {
            onEvent(FolderNotesEvent.NavigateToAddNote)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(state.notes) { note ->
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeTrash(onTrashClick = {
                                onEvent(FolderNotesEvent.OnDialogStateChange(true))
                                onEvent(FolderNotesEvent.OnNoteIdChange(note.id!!))
                            })
                        },
                        contentUnderLeft = {
                            SwipeEdit(onClick = {
                                if (state.folderId == note.folderId) {
                                    coroutineScope.launch {
                                        onEvent(FolderNotesEvent.OnNoteIdChange(note.id!!))
                                        delay(1)
                                        onEvent(FolderNotesEvent.NavigateToEditNote)
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
                                    onEvent(FolderNotesEvent.OnNoteIdChange(note.id!!))
                                    coroutineScope.launch {
                                        Constants.NOTE_TITLE = note.title
                                        Constants.NOTE_DESCRIPTION = note.description
                                        Constants.NOTE_DATE_TIME = note.dateCreated
                                        onEvent(FolderNotesEvent.NavigateToShowDetails)
                                    }
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