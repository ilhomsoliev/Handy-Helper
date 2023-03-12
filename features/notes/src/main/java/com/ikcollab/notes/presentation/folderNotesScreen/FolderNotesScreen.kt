package com.ikcollab.notes.presentation.folderNotesScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
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
import kotlinx.coroutines.launch
import java.sql.Date

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FoldersNoteScreen(
    state: FolderNotesState,
    onEvent: (FolderNotesEvent) -> Unit
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
            onEvent(FolderNotesEvent.DeleteNoteById)
            isDialogState.value = false
            /*note.id?.let { id ->
                onEvent(FoldersNotesScreenEvent.OnNoteIdChange(id))
                onEvent(FoldersNotesScreenEvent.DeleteNoteById)
                Log.e("Delete", "Success")
            }*/
        },
        cancelBtnClick = { isDialogState.value = false },
        isDialogOpen = isDialogState,
        okBtnText = "Delete",
        cancelBtnText = "Cancel"
    ) {
        isDialogState.value = false
    }

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
                            isDialogState.value = true
                            onEvent(FolderNotesEvent.OnNoteIdChange(note.id!!))
                        })
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            Constants.WHICH_NOTE.value = Constants.EDIT_NOTE
                            coroutineScope.launch {
                                if (state.folderId == note.folderId || note.folderId == -1) {
                                    note.id?.let { id ->
                                        onEvent(
                                            FolderNotesEvent.OnNoteIdChange(
                                                id
                                            )
                                        )
                                    }
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
                                /*coroutineScope.launch {
                                    note.id?.let { id ->
                                        onEvent(
                                            FoldersNotesScreenEvent.OnNoteIdChange(
                                                id
                                            )
                                        )
                                        Constants.NOTE_TITLE = note.title
                                        Constants.NOTE_DESCRIPTION = note.description
                                        Constants.NOTE_DATE_TIME = note.dateCreated
                                        onEvent(FoldersNotesScreenEvent.NavigateToShowDetails)
                                    }
                                }*/
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 25.dp, bottom = 25.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(backgroundColor = Color.Red, onClick = {
            onEvent(FolderNotesEvent.NavigateToAddNote)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}